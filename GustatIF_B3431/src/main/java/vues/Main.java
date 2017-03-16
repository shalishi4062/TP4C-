/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vues;

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
import metier.service.ServiceMetier;
import metier.service.ServiceTechnique;
import util.GeoTest;

/**
 *
 * @author B3431
 */


public class Main {
    
     
        
        static ServiceMetier smetier = new ServiceMetier();
        static ServiceTechnique stechnique = new ServiceTechnique();
        static Scanner clavier = new Scanner(System.in);
    
    public static void main(String[] args){
        
        Client client = null;
        System.out.println("Bienvenue sur l'application GustatIF !");
        System.out.println("Souhaitez vous vous connecter(1) ou vous inscrire(2) ?");
        int n = clavier.nextInt();
        while(n!=1 && n!=2){
            System.out.println("Veuillez taper (1) ou (2)");
            n = clavier.nextInt();
        }
        if(n==1){
            client = signIn();
        } else {
            client = signUp();
        }
        System.out.println("Vous êtes connectés !");
        n=-1;
        while(n!=0){
            n=-1;
            System.out.println("Voulez vous effectuer une commande (1) ou quitter l'application (0) ?");
            n = clavier.nextInt();
            if(n==1) createCommande(client);
        }
        
    }
    
    //Fonctions Console
    
    public static Client signIn(){
        System.out.println("Veuillez taper le mail avec lequel vous vous êtes inscrit :");
        String mail = clavier.nextLine();
        return smetier.singInClient(mail);
    }
    
    public static Client signUp(){
        System.out.println("Bienvenue sur les services de l'application GustatIF !");
        System.out.println("Veuillez taper..");
        System.out.println("Votre nom :");
        String nom = clavier.nextLine();
        System.out.println("Votre prénom :");
        String prenom = clavier.nextLine();
        System.out.println("Votre mail :");
        String mail = clavier.nextLine();
        System.out.println("Votre adresse :");
        String adresse = clavier.nextLine();
        return smetier.signUpClient(nom, prenom, mail, adresse);
    }
    
    public static void createCommande(Client client){
        
        int x = 2;
        List<Restaurant> restaurants = stechnique.getRestaurants();
        Restaurant restaurant = selectRestaurant(restaurants);
        Date date = selectDate();
        Commande commande = new Commande(client, date);
        smetier.createCommande(commande);
        while(x!=0){
            Qte_Commande qcommande = selectProduit(restaurant, commande);
            commande.addQteProduit(qcommande);
            System.out.println("Souhaitez vous un autre produit(1) ou ce sera tout(0) ? #Tapez le chiffre entre parenthèse lié à votre choix");
            x = clavier.nextInt();
        }
        stechnique.checkCommande(commande, restaurant);
        smetier.updateCommande(commande);
    }
    
    public static Restaurant selectRestaurant(List<Restaurant> restaurants){
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
    
    public static Qte_Commande selectProduit(Restaurant restaurant, Commande commande){
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
        stechnique.createQteCommande(qcommande);
        return qcommande;
    }
    
    public static Date selectDate(){
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
