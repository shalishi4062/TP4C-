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
        
        try { 
            Client client = cdao.findById(1100);
            Livreur livreur = new LivreurHumain("14 Avenue Albert Einstein 69100", 1400.0, 5843.0, 20.0, true,"Travolta", "John", "john.travolta@gustatif.fr");
            Date date = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
            String dateN = "15-11-96";
            date = dateFormat.parse(dateN);
            Commande c = new Commande(client,livreur, date );
            ldao.create(livreur);
            codao.create(c);
           // System.out.println(codao.findAllByClientID(1100));
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
                 
        
        
    
        JpaUtil.fermerEntityManager();
        JpaUtil.destroy();
        
        
    }
    
}
