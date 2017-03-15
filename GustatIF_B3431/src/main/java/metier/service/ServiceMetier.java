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
public class ServiceMetier {
    
    ClientDAO cdao = new ClientDAO();
    CommandeDAO codao = new CommandeDAO();
    LivreurDAO ldao = new LivreurDAO();
    ProduitDAO pdao = new ProduitDAO();
    Qte_CommandeDAO qdao = new Qte_CommandeDAO();
    RestaurantDAO rdao = new RestaurantDAO();
    ServiceTechnique stechnique = new ServiceTechnique();
    Scanner clavier = new Scanner(System.in);
    
    
    
    public void createCommande(Client client){
        JpaUtil.init();
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        try {
            int x = 2;
            List<Restaurant> restaurants = rdao.findAll();
            Restaurant restaurant = stechnique.selectRestaurant(restaurants);
            Date date = stechnique.selectDate();
            Livreur livreur = stechnique.findLivreur(client.getAdresse(),restaurant, date );
            Commande commande = new Commande(client, livreur, date);
            while(x!=0){
               Qte_Commande qcommande = stechnique.selectProduit(restaurant, commande);
               commande.addQteProduit(qcommande);
               System.out.println("Souhaitez vous un autre produit(1) ou ce sera tout(0) ? #Tapez le chiffre entre parenthèse lié à votre choix");
               x = clavier.nextInt();
            }
            codao.create(commande);
            if(commande.getPoidsTotal()>livreur.getCapacite()){
                livreur = stechnique.selectNewLivreur(commande.getPoidsTotal(), client, restaurant);
            }
            System.out.println("Votre commande : \n"+commande.getProduitsCommande()+ "\n a été confirmée et créee.");
            Date now = new Date();
            if(now.after(commande.getDate())){
                commande.setEtat(1);
            }
            //Envoyer Mails
            codao.update(commande);
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        JpaUtil.destroy();
    }
    
}
