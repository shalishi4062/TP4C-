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
    
    public Restaurant selectRestaurant(List<Restaurant> restaurants){
        int choix = -1;
        System.out.println("Choisissez un restaurant parmi ceux ci :");
        for(int i=1; i<restaurants.size(); i++){
            Restaurant resto = restaurants.get(i-1);
            System.out.println("("+i+")" + resto.getDenomination() + " : " + resto.getDescription());
        }
        System.out.println("Tapez le numéro du restaurant choisi.");
        choix = clavier.nextInt();
        return restaurants.get(choix-1);
    }
    
    public Qte_Commande selectProduit(Restaurant restaurant, Commande commande){
        int p;
        int quantite;
        int x = 0;
        System.out.println("Voici la liste des produits disponibles dans le restaurant : " + restaurant.getDenomination());
        List<Produit> produits = restaurant.getProduits();
        for(int i=1; i<produits.size(); i++){
            Produit produit = produits.get(i-1);
            System.out.println("("+i+")" + produit.getDenomination() + " : " + produit.getDescription());
        }
        System.out.println("Tapez le numéro du produit que vous souhaitez.");
        p = clavier.nextInt();
        System.out.println("Quelle quantité ?");
        quantite = clavier.nextInt();
        Qte_Commande qcommande = new Qte_Commande(commande, produits.get(p-1), quantite);
        try {
            qdao.create(qcommande);
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return qcommande;
    }
    
    
    public Livreur selectNewLivreur(double poids, Client client, Restaurant restaurant) {
        Livreur livreur;
        List<Livreur> livreurs = new ArrayList();
        List<Livreur> livreursDispo = new ArrayList();
        try {
            livreurs = ldao.findAll();
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    
    public Livreur findLivreur(String adresse, Restaurant restaurant, Date date){
        Livreur livreur;
        List<Livreur> livreurs = new ArrayList();
        boolean livreurDispo = false;
        try {
            livreurs = ldao.findAll();
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        double[] times = new double[livreurs.size()+1];
        for(int i=0; i<livreurs.size(); i++){
            livreur = livreurs.get(i);
            double tempsLivraison = GeoTest.getTripDurationByBicycleInMinute(GeoTest.getLatLng(livreur.getAdresse()),GeoTest.getLatLng(adresse), GeoTest.getLatLng(restaurant.getAdresse()));
            if(livreur.getDisponibilite()) {
                times[i] = tempsLivraison;
                if(!livreurDispo) livreurDispo = true;
            }
        }
        
        if(!livreurDispo){
            System.out.println("Aucun livreur disponible actuellement, veuillez réessayer plus tard");
            return null;
        } else {
            livreur = new LivreurHumain("", 0.0, false, "", "", "");
            int nbT = 1;
            while(!livreur.getDisponibilite() && nbT>0){
                nbT = 0;
                double res = 99999999999990.0;
                double temp = 0.0;
                int ires = -1;
                for(int i=0; i<livreurs.size(); i++){
                    if(!livreurs.get(i).getDisponibilite()){
                        times[i] = 0.0;
                    }
                    if(times[i] != 0.0){
                        temp = times[i];
                        nbT++;
                        if(temp<res){
                            res =temp;
                            ires = i;
                        }
                    }
                }
                if(ires!=-1){
                    livreur = livreurs.get(ires);
                } else {
                    System.out.println("Aucun livreur disponible actuellement, veuillez réessayer plus tard");
                    return null;
                }
            }
        }
        return livreur;
    }
    
    public Date selectDate(){
        Date date;
        System.out.println("Souhaitez vous être livré maintenant(1) ou à une date précise(2) ?");
        int choix = clavier.nextInt();
        date = new Date();
        String res ="";
        if(choix!=1){
            System.out.println("A quelle heure souhaitez vous être livré ? (XXhXX)");
            while(res.equals("")){
                res = clavier.nextLine();
            }
            int heure = Integer.parseInt(res.substring(0, 1));
            int minute = Integer.parseInt(res.substring(3));
            date.setHours(heure);
            date.setMinutes(minute);
        }
        return date;
    }

    
}
