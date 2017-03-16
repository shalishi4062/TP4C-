/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

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
        Livreur livreur = selectNewLivreur(commande.getPoidsTotal(), client, restaurant);
        commande.setLivreur(livreur);
        if(commande.getPoidsTotal()>livreur.getCapacite()){
              livreur = selectNewLivreur(commande.getPoidsTotal(), client, restaurant);
        }
        commande.setLivreur(livreur);
        envoiMailLivreur(livreur, commande, restaurant);
        livreur.livrer();
        System.out.println("Votre commande : \n"+commande.getProduitsCommande()+ "\n a été confirmée et créee.");
        Date now = new Date();
        if(now.after(commande.getDateDeb())){
            commande.setEtat(1);
        }
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
    
    public Livreur selectNewLivreur(double poids, Client client, Restaurant restaurant) {
        Livreur livreur;
        List<Livreur> livreursDispo = new ArrayList();
        List<Livreur> livreurs = getLivreurs();
        for(int i=0; i<livreurs.size(); i++){
            Livreur liv = livreurs.get(i);
            if(liv.getDisponibilite() && liv.getCapacite()>=poids){
                livreursDispo.add(liv);
            }
        }
        double time = 99999999;
        double temp = 99999999;
        int ires =0;
        for(int i=0; i<livreursDispo.size(); i++){
            Livreur l = livreursDispo.get(i);
            temp = GeoTest.getTripDurationByBicycleInMinute(GeoTest.getLatLng(l.getAdresse()),GeoTest.getLatLng(client.getAdresse()), GeoTest.getLatLng(restaurant.getAdresse()));
            if(temp<time){
                time = temp;
                ires = i;
            }
        }
        if(livreursDispo.size()>0){
            livreur = livreursDispo.get(ires);
            
        } else {
            System.out.println("Votre commande est trop lourde");
            return null;
        }
        return livreur;
            
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
