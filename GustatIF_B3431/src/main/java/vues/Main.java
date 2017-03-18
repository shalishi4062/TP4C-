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


public class Main {
    
        static ServiceMetier smetier = new ServiceMetier();
        static ServiceTechnique stechnique = new ServiceTechnique();
        static Scanner clavier = new Scanner(System.in);
    
    public static void main(String[] args){
        
        System.out.println("Bienvenue sur l'application GustatIF !");
        
        Client client = null;
        Integer n = firstPage();
        List<Integer> l = Arrays.asList(1,2);
        
        if(n==1){
            client = signIn();
            while (client == null){
                Integer i = lireInteger(" Voulez vous retaper votre adresse mail (1) ou vous vous inscrire (2) ?",l);
                if(i==1 && i!=null){
                    client = signIn();
                }else if(i!=null) {
                    client = signUp();
                }
            }
        } else {
            client = signUp();
        }
        System.out.println("Vous êtes connectés !");
        n=-1;
        List<Integer> l1 = Arrays.asList(0,1);
        while(n!=0){
            n=-1;
            n = lireInteger("Voulez vous effectuer une commande (1) ou quitter l'application (0) ?", l1);
            if(n==1) createCommande(client);
        }
        
    }
    
    //Fonctions Console
    
    public static Integer firstPage(){
        
        List<Integer> l = Arrays.asList(1,2);
        Integer n = lireInteger("Souhaitez vous vous connecter(1) ou vous inscrire(2) ?", l);;
        while(n!=1 && n!=2){
            n = lireInteger("Souhaitez vous vous connecter(1) ou vous inscrire(2) ?", l);
        }
        return n;
        
    }
    
    public static Client signIn(){
        System.out.println("Afin de vous connecter..");
        String mail = lireChaine("Veuillez taper le mail avec lequel vous vous êtes inscrit :");
        return stechnique.singInClient(mail);
    }
    
    public static Client signUp(){
        
        System.out.println("Bienvenue sur les services de l'application GustatIF !");
        System.out.println("Veuillez taper..");
        String nom = lireChaine("Votre nom :");
        String prenom = lireChaine("Votre prénom :");
        String mail = lireChaine("Votre mail :");
        String adresse = lireChaine("Votre adresse :");
        return stechnique.signUpClient(nom, prenom, mail, adresse);
    }
    
    public static void createCommande(Client client){
        
        int x = 2;
        List<Restaurant> restaurants = stechnique.getRestaurants();
        Restaurant restaurant = selectRestaurant(restaurants);
        Date date = selectDate();
        Commande commande = new Commande(client, date, restaurant);
        stechnique.createCommande(commande);
        while(x!=0){
            Qte_Commande qcommande = selectProduit(restaurant, commande);
            commande.addQteProduit(qcommande);
            x = lireInteger("Souhaitez vous un autre produit(1) ou ce sera tout(0) ? "
                    + "#Tapez le chiffre entre parenthèse lié à votre choix");
        }
        smetier.checkCommande(commande, restaurant);
    }
    
    public static Restaurant selectRestaurant(List<Restaurant> restaurants){
        Integer choix = null;
        List<Integer> l = new ArrayList<>();
        for (Integer j= 1; j<=restaurants.size(); j++){
            l.add(j);
        }
        System.out.println("Choisissez un restaurant parmi ceux ci :");
        for(int i=1; i<restaurants.size(); i++){
            Restaurant resto = restaurants.get(i-1);
            System.out.println("("+i+")" + resto.getDenomination() + " : " + resto.getDescription());
        }
        while (choix == null){
            choix = lireInteger("Tapez le numéro du restaurant choisi.",l);
        }
        return restaurants.get(choix-1);
    }
    
    public static Qte_Commande selectProduit(Restaurant restaurant, Commande commande){
        Integer p = null;
        int quantite;
        int x = 0;
        System.out.println("Voici la liste des produits disponibles dans le restaurant " + restaurant.getDenomination() + " : ");
        List<Produit> produits = restaurant.getProduits();
        for(int i=1; i<produits.size(); i++){
            Produit produit = produits.get(i-1);
            System.out.println("("+i+")" + produit.getDenomination() + " : " + produit.getDescription());
        }
        List<Integer> l = new ArrayList<>();
        for (Integer j= 1; j<=produits.size(); j++){
            l.add(j);
        }
        while (p == null){
            p = lireInteger("Tapez le numero du produit que vous souhaitez.",l);
        }
        quantite = lireInteger("Quelle quantité ?");
        Qte_Commande qcommande = new Qte_Commande(commande, produits.get(p-1), quantite);
        stechnique.createQteCommande(qcommande);
        return qcommande;
    }
    
    public static Date selectDate(){
        Date date;
        List<Integer> l = Arrays.asList(1,2);
        Integer choix = null;
        while (choix == null){
            choix= lireInteger("Souhaitez vous être livré maintenant(1) ou à une date précise(2) ?", l);
        }
        date = new Date();
        String res ="";
        if(choix!=1){
            while(res.equals("")){
                res = lireChaine("A quelle heure souhaitez vous être livré ? (XXhXX)");
            }
            int heure = Integer.parseInt(res.substring(0, 1));
            int minute = Integer.parseInt(res.substring(3));
            date.setHours(heure);
            date.setMinutes(minute);
        }
        return date;
    }
    
}