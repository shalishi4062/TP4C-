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
import metier.modele.Produit;
import metier.modele.Qte_Commande;
import metier.modele.Restaurant;
import util.GeoTest;
import static util.GeoTest.getLatLng;

/**
 *
 * @author jcharlesni
 */
public class ServiceTechnique {
    
    ClientDAO cdao = new ClientDAO();
    CommandeDAO codao = new CommandeDAO();
    LivreurDAO ldao = new LivreurDAO();
    ProduitDAO pdao = new ProduitDAO();
    Qte_CommandeDAO qdao = new Qte_CommandeDAO();
    RestaurantDAO rdao = new RestaurantDAO();
    Scanner clavier = new Scanner(System.in);
    
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
    
    public void updateCommande(Commande commande){
        JpaUtil.init();
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        try {
            //Envoyer Mails
            codao.update(commande);
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        JpaUtil.destroy();
    }
    
    public void updateLivreur(Livreur livreur){
        JpaUtil.init();
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        try {
            //Envoyer Mails
            ldao.update(livreur);
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        JpaUtil.destroy();
    }
    
    public Client signUpClient(String nom, String prenom, String mail, String adresse) {
        
        LatLng latlong = getLatLng(adresse);
        
        JpaUtil.init();
        JpaUtil.creerEntityManager();
        Client cl = new Client(nom, prenom, mail, adresse);
        try {
            JpaUtil.ouvrirTransaction();
            cdao.create(cl);
            JpaUtil.validerTransaction();
            envoiMailInscription(1, cl);
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
            envoiMailInscription(0, cl);
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
    
    public void envoiMailInscription(int etat, Client cl){
        //si 0 echec si 1 reussi
        System.out.println("Expediteur : gustatif@gustatif.com\n"
                    + "Pour : " + cl.getMail() +"\n"
                            + "Sujet : Bienvenue chez Gustatif\n"
                            + "Corps :\n"
                            + "Bonjour "+ cl.getPrenom() + ",\n");
        if(etat == 1){
            System.out.println("Nous vous confirmons votre inscription au service Gustat'IF. "
                    + "Votre numero de client est "+ cl.getId() + ".\n");
        }else{
            System.out.println("Votre inscription au service gustat'IF a malheureusement échouée..."
                    + " Merci de recommencer ultériorement.\n");
        }
    }
    
    public void envoiMailLivreur(Livreur li, Commande co, Restaurant r){
        //si 0 echec si 1 reussi
      
            System.out.println("Expediteur : gustatif@gustatif.com\n"
                    + "Pour : " + li.getNom()+ " " + li.getPrenom() + " <" +li.getMail() +">\n"
                            + "Sujet :Livraison n°" + co.getID() + "\n"
                            + "Corps :\n"
                            + "Bonjour "+ li.getPrenom() + ",\n"
                            +"Merci d'effectuer cette livraison dès maintenant tout en respectant le code de la route >.^\n"
                            + "Le Chef\n"
                                    + "Détails de la livraison\n"
                                        + "\t-date/heure :" + co.getDateDeb()+"\n"
                                        + "\t-Livreur : " + li.getPrenom() +" "+li.getNom()+" (n°"+li.getId()+")\n"
                                        + "\t-Restaurant : "+ r.getDenomination()+"\n"
                                        + "\t-Client :\n"+ co.getClient().getPrenom() + " "+co.getClient().getNom()+"\n"
                                                + "\t"+co.getClient().getAdresse()+"\n\n"
                                    + "Commande : \n"
                                                        + "\t-a continuer !!!!!!!!!!!!!!!");
        
        
                                                

    }
    
    
}
