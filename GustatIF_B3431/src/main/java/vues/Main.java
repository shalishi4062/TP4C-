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
import java.util.logging.Level;
import java.util.logging.Logger;
import metier.modele.Client;
import metier.modele.Commande;
import metier.modele.Livreur;
import metier.modele.LivreurHumain;
import metier.modele.Produit;
import metier.modele.Qte_Commande;
import metier.modele.Restaurant;

/**
 *
 * @author B3431
 */
public class Main {
    
    public static void main(String[] args){
        
        JpaUtil.init();
        JpaUtil.creerEntityManager();
        ClientDAO cdao = new ClientDAO();
        CommandeDAO codao = new CommandeDAO();
        LivreurDAO ldao = new LivreurDAO();
        ProduitDAO pdao = new ProduitDAO();
        Qte_CommandeDAO qdao = new Qte_CommandeDAO();
        try {
            JpaUtil.ouvrirTransaction();
            Commande c = codao.findById(1201);
            JpaUtil.validerTransaction();
            System.out.println(codao.getProduitsCommandeById(c.getID()));
           // System.out.println(codao.findAllByClientID(1100));
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
                 
        
        
    
        JpaUtil.fermerEntityManager();
        JpaUtil.destroy();
        
        
    }
    
}
