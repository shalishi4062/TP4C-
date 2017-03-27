/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import com.google.maps.model.LatLng;
import dao.ClientDAO;
import dao.CommandeDAO;
import dao.JpaUtil;
import dao.LivreurDAO;
import dao.ProduitDAO;
import dao.Qte_CommandeDAO;
import dao.RestaurantDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import metier.modele.Client;
import metier.modele.Commande;
import metier.modele.Livreur;
import metier.modele.LivreurHumain;
import metier.modele.LivreurMachine;
import metier.modele.Qte_Commande;
import metier.modele.Restaurant;
import static util.GeoTest.getLatLng;

/**
 *
 * @author jcharlesni
 */
public class ServiceMetier {

    ClientDAO cdao = new ClientDAO();
    CommandeDAO codao = new CommandeDAO();
    LivreurDAO ldao = new LivreurDAO();
    ProduitDAO pdao = new ProduitDAO();
    Qte_CommandeDAO qdao = new Qte_CommandeDAO();
    RestaurantDAO rdao = new RestaurantDAO();
    ServiceTechnique stechnique = new ServiceTechnique();
    Scanner clavier = new Scanner(System.in);

    public Client signUpClient(Client cl) {
        JpaUtil.creerEntityManager();
        LatLng latlong = getLatLng(cl.getAdresse());

        try {
            JpaUtil.ouvrirTransaction();
            cdao.create(cl);
            JpaUtil.validerTransaction();
            stechnique.envoiMailInscription(1, cl);
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
            stechnique.envoiMailInscription(0, cl);
        }
        JpaUtil.fermerEntityManager();

        return cl;
    }

    public Client singInClient(String mail) {
        JpaUtil.creerEntityManager();
        ClientDAO cdao = new ClientDAO();
        Client cl = null;
        try {
            cl = cdao.findClientByMail(mail);
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        JpaUtil.fermerEntityManager();
        return cl;
    }

    public void createCommande(Commande commande) {
        JpaUtil.creerEntityManager();

        try {
            JpaUtil.ouvrirTransaction();
            codao.create(commande);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }

        JpaUtil.fermerEntityManager();
    }

    public void createQteCommande(Qte_Commande qcommande) {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        try {
            qdao.create(qcommande);
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
    }

    public void checkCommande(Commande commande, Restaurant restaurant) {
        Client client = commande.getClient();
        boolean reussi = false;
        Livreur livreur = stechnique.selectNewLivreur(commande.getPoidsTotal(), client, restaurant);
        if (livreur != null) {
            while (!reussi) {
                commande.setLivreur(livreur);
                livreur.setDisponibilite(false);
                commande.setEtat(1);
                try {
                    stechnique.updateLivreur(livreur);
                    reussi = true;
                } catch (Exception ex) {
                    reussi = false;
                    livreur.setDisponibilite(true);
                    livreur = stechnique.selectNewLivreur(commande.getPoidsTotal(), client, restaurant);
                    if(livreur == null){
                        commande.setEtat(3);
                        System.out.println("Aucun livreur disponible pour cette commande. Veuillez recommencer plus tard..)");
                    }
                }
            }
            createCommande(commande);
            if (livreur instanceof LivreurHumain) {
                stechnique.envoiMailLivreur(livreur, commande, restaurant);
            }
            System.out.println("Votre commande : \n" + commande.getProduitsCommande() + "\n a été confirmée et créee.");
            livreur.prendreCommande();
        } else {
            System.out.println("Veuillez entrer une nouvelle commande avec moins de produits");
        }
    }

    /*public void validerCommande(Livreur l) throws Exception {
        l.setDisponibilite(false);
        try {
            stechnique.updateLivreur(l);
        } catch (Exception ex) {
            throw ex;
        }
    }//elle sert*/
    public void finirCommande(Commande commande, Livreur livreur) {
        //commande.getLivreur().finirCommande(commande);
        livreur.finirCommande(commande);
        stechnique.updateCommande(commande);
    }

    public List<Restaurant> getRestaurants() {
        JpaUtil.creerEntityManager();
        List<Restaurant> restaurants = new ArrayList();
        try {
            restaurants = rdao.findAll();
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        JpaUtil.fermerEntityManager();
        return restaurants;
    }

    public List<Client> getClients() {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        List<Client> clients = new ArrayList();
        try {
            clients = cdao.findAll();
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        return clients;
    }

    public List<Livreur> getLivreurs() {
        return stechnique.getLivreurs();
    }

    public List<Commande> getCommandes() {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        List<Commande> commandes = new ArrayList();
        try {
            commandes = codao.findAll();
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        return commandes;
    }
    
    public List<Commande> getCommandesForLivreur(Livreur li) {
        return li.getCommandes();
    }

    public void createLivreurs() {
        JpaUtil.creerEntityManager();
        LivreurHumain h1 = new LivreurHumain("8 Rue Arago, Villeurbanne", 25000.0,
                "Premier", "Fisrt", "premier@gustatif.fr");
        LivreurHumain h2 = new LivreurHumain("80 Rue Léon Fabre, Villeurbanne", 23000.93,
                "Deuxieme", "Second", "deuxieme@gustatif.fr");
        LivreurHumain h3 = new LivreurHumain("8 Rue Wilhelmine, Villeurbanne", 20000.95,
                "Troisieme", "Third", "troisieme@gustatif.fr");
        LivreurHumain h4 = new LivreurHumain("9 Place de la Paix", 18000.79,
                "Quatrieme", "Forth", "quatrieme@gustatif.fr");
        LivreurHumain h5 = new LivreurHumain("3 Allée Louis Pergaud", 21500.9,
                "Cinquieme", "Fifth", "cinquieme@gustatif.fr");
        LivreurMachine m1 = new LivreurMachine("20 Rue des Peupliers, Villeurbanne", 1500.0,
                "R1G1", 55.9);
        LivreurMachine m2 = new LivreurMachine("7 Rue Pelisson, Villeurbanne", 2000.0,
                "R2G2", 50.5);
        LivreurMachine m3 = new LivreurMachine("16 Boulevard Niels Bohr, Villeurbanne", 2500.0,
                "R3G3", 60.5);
        LivreurMachine m4 = new LivreurMachine("11 Rue Mansard, Villeurbanne", 1700.0,
                "R4G4", 80.5);
        LivreurMachine m5 = new LivreurMachine("12 Rue Léon Piat, Villeurbanne", 2300.0,
                "R5G5", 45.5);
        try {
            JpaUtil.ouvrirTransaction();
            ldao.create(h1);
            ldao.create(h2);
            ldao.create(h3);
            ldao.create(h4);
            ldao.create(h5);
            ldao.create(m1);
            ldao.create(m2);
            ldao.create(m3);
            ldao.create(m4);
            ldao.create(m5);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        JpaUtil.fermerEntityManager();
    }

}
