/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import dao.ClientDAO;
import dao.CommandeDAO;
import dao.JpaUtil;
import dao.LivreurDAO;
import dao.ProduitDAO;
import dao.Qte_CommandeDAO;
import dao.RestaurantDAO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import metier.service.ServiceMetier;
import metier.service.ServiceTechnique;
import util.GeoTest;
import static util.Saisie.lireChaine;
import static util.Saisie.lireInteger;

/**
 *
 * @author B3431
 */

public class MainGestion {
    
        static ServiceMetier smetier = new ServiceMetier();
    
    public static void main(String[] args) throws Exception{
        
         System.out.println("Bienvenue administrateur de l'application GustatIF !");
         
          List<Integer> l = Arrays.asList(0,1,2,3,4);
          Integer i = null;
          
          while (i == null || i != 0){
            i = lireInteger("Souhaitez vous voir tous les restaurants (1), livreurs (2), "
                    + "clients (3), livraisons en cours (4) ou quitter (0) ?",l);
            
          switch (i){
              case 1:
                  visualisationRestaurants();
                  break;
              case 2 :
                  visualisationLivreurs(); //faux on a pas les lat long mais l'adresse
                  break;
              case 3 :
                  visualisationClients();
                  break;
              case 4 :
                  visualisationCommandesEnCours(); //a tester avec commandes
                  break;
              default :
                  break;
          }
    }
    }
    
    public static void visualisationRestaurants(){
        List<Restaurant> restaurants = smetier.getRestaurants();
        for(int i=0; i<restaurants.size(); i++){
            System.out.println("("+i+")" + restaurants.get(i).getDenomination() + 
                    " Lattitude : " + restaurants.get(i).getLatitude() + " Longitude : "+restaurants.get(i).getLongitude());
        }
    }
    
     public static void visualisationLivreurs(){
        List<Livreur> livreurs = smetier.getLivreurs();
        for(int i=0; i<livreurs.size(); i++){
            System.out.println("("+i+")" + livreurs.get(i).getNom()+ 
                   " Adresse : " + livreurs.get(i).getAdresse());
        }
    }
     
    public static void visualisationClients(){
        List<Client> clients = smetier.getClients();
        for(int i=0; i<clients.size(); i++){
            System.out.println("("+i+")" + clients.get(i).getPrenom()+ " " + clients.get(i).getNom() +
                    " Lattitude : " + clients.get(i).getLatitude() + " Longitude : "+ clients.get(i).getLongitude());
        }
    }
      
    public static void visualisationCommandesEnCours(){
        List<Commande> commandes = smetier.getCommandes();
        for(int i=0; i<commandes.size(); i++){
            if(commandes.get(i).getEtat().equals("En cours"))
            System.out.println("Clients : " + commandes.get(i).getClient() +" Restaurant : " + commandes.get(i).getRestaurant() +
                    " heure de debut : " + commandes.get(i).getDateDeb());
        }
    }
    
}