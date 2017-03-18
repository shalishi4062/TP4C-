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
public class ServiceMetier {
    
    ClientDAO cdao = new ClientDAO();
    CommandeDAO codao = new CommandeDAO();
    LivreurDAO ldao = new LivreurDAO();
    ProduitDAO pdao = new ProduitDAO();
    Qte_CommandeDAO qdao = new Qte_CommandeDAO();
    RestaurantDAO rdao = new RestaurantDAO();
    ServiceTechnique stechnique = new ServiceTechnique();
    Scanner clavier = new Scanner(System.in);
    
    
    
    public void checkCommande(Commande commande, Restaurant restaurant){
        
        Client client = commande.getClient();
        Livreur livreur = selectNewLivreur(commande.getPoidsTotal(), client, restaurant);
        commande.setLivreur(livreur);
        if(commande.getPoidsTotal()>livreur.getCapacite()){
            livreur = selectNewLivreur(commande.getPoidsTotal(), client, restaurant);
        }
        commande.setLivreur(livreur);
        if(livreur instanceof LivreurHumain) stechnique.envoiMailLivreur(livreur, commande, restaurant);
        livreur.livrer();
        stechnique.updateCommande(commande);
        stechnique.updateLivreur(livreur);
        System.out.println("Votre commande : \n"+commande.getProduitsCommande()+ "\n a été confirmée et créee.");
        
        
    }
    
    public Livreur selectNewLivreur(double poids, Client client, Restaurant restaurant) {
        Livreur livreur;
        List<Livreur> livreursDispo = new ArrayList();
        List<Livreur> livreurs = stechnique.getLivreurs();
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
    
    
    
}
