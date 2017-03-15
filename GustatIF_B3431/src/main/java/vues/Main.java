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
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import metier.modele.Client;
import metier.modele.Commande;
import metier.modele.Livreur;
import metier.modele.LivreurHumain;
import metier.modele.Produit;
import metier.modele.Qte_Commande;
import metier.modele.Restaurant;
import static metier.service.ServiceMetier.*;

/**
 *
 * @author B3431
 */
public class Main {
    
    public static void main(String[] args){
        
        // signUpClient("Shi", "Shali", "shalishi@gmail.com", "20 avenue Albert Einstein 69100 Villeurbanne");
        //Client c = singInClient("shalishi@gmail.com");
        List <Restaurant> rs = consultListRestaurant();
        List<Produit> rp =null ;
         System.out.println("Liste des restaurants : ");
        for(int i=0; i<rs.size(); i++){
            System.out.println(rs.get(i).toString());
        }
        rp=consultListProduit(rs.get(rs.size()-1).getId()); 
          System.out.println("Liste des produits : ");
        for(int i=0; i<rp.size(); i++){
            System.out.println(rp.get(i).toString());
        }
    }
}
