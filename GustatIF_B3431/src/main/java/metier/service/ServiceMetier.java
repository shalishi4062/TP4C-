/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import com.google.maps.model.LatLng;
import dao.ClientDAO;
import dao.JpaUtil;
import dao.LivreurDAO;
import dao.RestaurantDAO;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import metier.modele.Client;
import metier.modele.Commande;
import metier.modele.Livreur;
import metier.modele.Produit;
import metier.modele.Restaurant;
import static util.GeoTest.getLatLng;

/**
 *
 * @author jcharlesni
 */
public class ServiceMetier {
    
    public static void signUpClient(String nom, String prenom, String numero, String mail, String adresse) {
        
        LatLng latlong = getLatLng(adresse);
        
        JpaUtil.init();
        JpaUtil.creerEntityManager();
        ClientDAO cdao = new ClientDAO();
        Client cl = new Client(nom, prenom, numero, mail, adresse);
        try {
            JpaUtil.ouvrirTransaction();
            cdao.create(cl);
            JpaUtil.validerTransaction();
            ServiceTechnique.envoiMailInscription(1,cl);
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
            ServiceTechnique.envoiMailInscription(0,cl);
        }
        JpaUtil.fermerEntityManager();
        JpaUtil.destroy();
    }
    
    public static Client singInClient(String mail){
        JpaUtil.init();
        JpaUtil.creerEntityManager();
        ClientDAO cdao = new ClientDAO();
        Client cl = null;
        try {
            List<Client> clients = cdao.findAll(); 
            for (int i = 0; i < clients.size(); i++) {
		if(clients.get(i).getMail().equals(mail)){
                    System.out.println("Bienvenue "+cl.getPrenom());
                    JpaUtil.fermerEntityManager();
                    JpaUtil.destroy();
                    return clients.get(i);
                }
	}
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Vous n'êtes pas encore inscrit!");
        JpaUtil.fermerEntityManager();
        JpaUtil.destroy();
        return cl;
    }
    
    public static List<Restaurant> consultListRestaurant (){
        JpaUtil.init();
        JpaUtil.creerEntityManager();
        List<Restaurant> restos = null;
        RestaurantDAO rdao= new RestaurantDAO();
        try {     
            restos = rdao.findAll();          
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        JpaUtil.fermerEntityManager();
        JpaUtil.destroy();
        return restos;
    }
    
      public static List<Produit> consultListProduit (long idr){
            JpaUtil.init();
            JpaUtil.creerEntityManager();
            Restaurant resto = null;
            RestaurantDAO rdao= new RestaurantDAO();
        try {     
            resto = rdao.findById(idr);
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        JpaUtil.fermerEntityManager();
        JpaUtil.destroy();
        return resto.getProduits();
    }    
      
      public static void changerEtatLivreur (Livreur li, boolean b){
         li.setDisponibilite(b);
     }
      
      public static void setDateFin (Commande c, Date d){
         c.setdateFin(d);
     }
      
      public static Map<Long,LatLng> consultListDepartLivreur (){
          
            JpaUtil.init();
            JpaUtil.creerEntityManager();
            LivreurDAO ld= new LivreurDAO();
            Map<Long,LatLng> mapLiv = null;
        try {     
            
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        JpaUtil.fermerEntityManager();
        JpaUtil.destroy();
        return mapLiv;
    }  
}
