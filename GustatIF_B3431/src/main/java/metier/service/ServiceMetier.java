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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import metier.modele.Client;
import metier.modele.Commande;
import metier.modele.Livreur;
import metier.modele.LivreurHumain;
import metier.modele.LivreurMachine;
import metier.modele.Produit;
import metier.modele.Qte_Commande;
import metier.modele.Restaurant;
import util.GeoTest;
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
    
    public Client signUpClient(String nom, String prenom, String mail, String adresse) {
        
        LatLng latlong = getLatLng(adresse);
        
        JpaUtil.init();
        JpaUtil.creerEntityManager();
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
        JpaUtil.destroy();
        return cl;
    }
    
    public Client singInClient(String mail){
        JpaUtil.init();
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
        JpaUtil.destroy();
        return cl;
    }
    
    public void createCommande(Commande commande){
        JpaUtil.init();
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        try {
            codao.create(commande);
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        JpaUtil.destroy();
    }
    
    public void createQteCommande(Qte_Commande qcommande){
        JpaUtil.init();
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        try {
            qdao.create(qcommande);
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        JpaUtil.destroy();
    }
    
    public void checkCommande(Commande commande, Restaurant restaurant){
        
        Client client = commande.getClient();
        Livreur livreur = stechnique.selectNewLivreur(commande.getPoidsTotal(), client, restaurant);
        commande.setLivreur(livreur);
        if(commande.getPoidsTotal()>livreur.getCapacite()){
            livreur = stechnique.selectNewLivreur(commande.getPoidsTotal(), client, restaurant);
        }
        commande.setLivreur(livreur);
        if(livreur instanceof LivreurHumain) stechnique.envoiMailLivreur(livreur, commande, restaurant);
        livreur.livrer();
        stechnique.updateCommande(commande);
        stechnique.updateLivreur(livreur);
        System.out.println("Votre commande : \n"+commande.getProduitsCommande()+ "\n a été confirmée et créee.");

    }
    
    public List<Restaurant> getRestaurants(){
        JpaUtil.init();
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
        JpaUtil.destroy();
        return restaurants;
    }
        
    public List<Livreur> getLivreurs(){
        JpaUtil.init();
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        List<Livreur> livreurs = new ArrayList();
        try {
            livreurs = ldao.findAll();
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        JpaUtil.destroy();
        return livreurs;
    }

    public List<Client> getClients(){
        JpaUtil.init();
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
        JpaUtil.destroy();
        return clients;
    }
    
    public List<Commande> getCommandes(){
        JpaUtil.init();
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
        JpaUtil.destroy();
        return commandes;
    }
    
    public void createLivreurs (){
        JpaUtil.init();
        JpaUtil.creerEntityManager();
        LivreurHumain h1 = new LivreurHumain("8 Rue Arago, Villeurbanne", 50852.0,
                "Premier", "Fisrt", "premier@gustatif.fr");
        LivreurHumain h2 = new LivreurHumain("80 Rue Léon Fabre, Villeurbanne", 704050.93,
                "Deuxieme", "Second", "deuxieme@gustatif.fr");
        LivreurHumain h3 = new LivreurHumain("8 Rue Wilhelmine, Villeurbanne", 87050.95,
                "Troisieme", "Third", "troisieme@gustatif.fr");
        LivreurHumain h4 = new LivreurHumain("9 Place de la Paix", 60540.79,
                "Quatrieme", "Forth", "quatrieme@gustatif.fr");
        LivreurHumain h5 = new LivreurHumain("3 Allée Louis Pergaud", 904562.9,
                "Cinquieme", "Fifth", "cinquieme@gustatif.fr");
        LivreurMachine m1 = new LivreurMachine("20 Rue des Peupliers, Villeurbanne", 1000.0,
                "R1G1", 450.9);
        LivreurMachine m2 = new LivreurMachine("7 Rue Pelisson, Villeurbanne", 2000.0,
                "R2G2", 381.5);
        LivreurMachine m3 = new LivreurMachine("16 Boulevard Niels Bohr, Villeurbanne", 3000.0,
                "R3G3", 1000.5);
        LivreurMachine m4 = new LivreurMachine("11 Rue Mansard, Villeurbanne", 4000.0,
                "R4G4", 200.5);
        LivreurMachine m5 = new LivreurMachine("12 Rue Léon Piat, Villeurbanne", 5000.0,
                "R5G5", 805.5);
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
        JpaUtil.destroy();
    }
    
}
