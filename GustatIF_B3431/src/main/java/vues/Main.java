/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vues;

import dao.ClientDAO;
import dao.JpaUtil;
import dao.ProduitDAO;
import dao.RestaurantDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import metier.modele.Client;
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
        RestaurantDAO rdao = new RestaurantDAO();
        ProduitDAO pdao = new ProduitDAO();
        
        try {
            cdao.consultList();
            rdao.consultList();
            pdao.consultList();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
        JpaUtil.fermerEntityManager();
        JpaUtil.destroy();
        
        
    }
    
}
