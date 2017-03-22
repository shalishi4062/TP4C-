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
 * @author B3431
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
    
    public Client signUpClient(String nom, String prenom, String mail, String adresse) {
        JpaUtil.creerEntityManager();
        LatLng latlong = getLatLng(adresse);
        Client cl = new Client(nom, prenom, mail, adresse);
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
    
    public Client singInClient(String mail){
        JpaUtil.creerEntityManager();
        ClientDAO cdao = new ClientDAO();
        List<Client> clients = new ArrayList();
        Client cl = null;
        try {
            clients = cdao.findAll(); 
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < clients.size(); i++) {
		if(clients.get(i).getMail().equals(mail)){
                    cl = clients.get(i);
                    System.out.println("Bienvenue "+cl.getPrenom());
                }
        }
        if(cl==null) System.out.println("Vous n'êtes pas encore inscrit!");
        JpaUtil.fermerEntityManager();
        return cl;
    }
    
    public void createCommande(Commande commande){
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
    
    public void createQteCommande(Qte_Commande qcommande){
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
    
    public void checkCommande(Commande commande, Restaurant restaurant){
        
        Client client = commande.getClient();
        Livreur livreur = stechnique.selectNewLivreur(commande.getPoidsTotal(), client, restaurant);
       if (livreur != null){
           commande.setLivreur(livreur);
            while(!livreur.getDisponibilite() && livreur !=null){
                livreur = stechnique.selectNewLivreur(commande.getPoidsTotal(), client, restaurant);
                commande.setLivreur(livreur);
            }
            updateCommande(commande);
            stechnique.updateLivreur(livreur);
            System.out.println("Votre commande : \n"+commande.getProduitsCommande()+ "\n a été confirmée et créee.");
            if(livreur instanceof LivreurHumain) stechnique.envoiMailLivreur(livreur, commande, restaurant);
            livreur.prendreCommande();
            stechnique.updateLivreur(livreur);
       } else {
           System.out.println("Veuillez entrer une nouvelle commande avec moins de produits");
       }
    }
    
    public List<Restaurant> getRestaurants(){
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        List<Restaurant> restaurants = new ArrayList();
        try {
            restaurants = rdao.findAll();
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        return restaurants;
    }

    public List<Client> getClients(){
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
    
    public List<Commande> getCommandes(){
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
    
    public void createLivreurs (){
        JpaUtil.creerEntityManager();
        LivreurHumain h1 = new LivreurHumain("8 Rue Arago, Villeurbanne", 25852.0,
                "Premier", "Fisrt", "premier@gustatif.fr");
        LivreurHumain h2 = new LivreurHumain("80 Rue Léon Fabre, Villeurbanne", 18050.93,
                "Deuxieme", "Second", "deuxieme@gustatif.fr");
        LivreurHumain h3 = new LivreurHumain("8 Rue Wilhelmine, Villeurbanne", 20050.95,
                "Troisieme", "Third", "troisieme@gustatif.fr");
        LivreurHumain h4 = new LivreurHumain("9 Place de la Paix", 22540.79,
                "Quatrieme", "Forth", "quatrieme@gustatif.fr");
        LivreurHumain h5 = new LivreurHumain("3 Allée Louis Pergaud", 26562.9,
                "Cinquieme", "Fifth", "cinquieme@gustatif.fr");
        LivreurMachine m1 = new LivreurMachine("20 Rue des Peupliers, Villeurbanne", 2000.0,
                "R1G1", 45.9);
        LivreurMachine m2 = new LivreurMachine("7 Rue Pelisson, Villeurbanne", 2000.0,
                "R2G2", 38.5);
        LivreurMachine m3 = new LivreurMachine("16 Boulevard Niels Bohr, Villeurbanne", 2300.0,
                "R3G3", 50.5);
        LivreurMachine m4 = new LivreurMachine("11 Rue Mansard, Villeurbanne", 2200.0,
                "R4G4", 20.5);
        LivreurMachine m5 = new LivreurMachine("12 Rue Léon Piat, Villeurbanne", 1900.0,
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
    
    public void updateCommande(Commande commande){
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        try {
            codao.update(commande);
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
    }
    
    
    
}
