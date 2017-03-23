/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author jcharlesni
 */

@Entity
public class Qte_Commande implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    
    private int quantite;
    
    @ManyToOne
    private Commande commande;
    
    @ManyToOne
    private Produit produit;
    
    protected Qte_Commande(){
        
    }
    
    public Qte_Commande(Commande c, Produit p, int q){
        commande = c;
        produit = p;
        quantite = q;
    }
    
    public Commande getCommande(){
        return commande;
    }
    
    public Produit getProduit(){
        return produit;
    }
    
    public int getQuantite(){
        return quantite;
    }
    
    public void setCommande(Commande c){
        commande = c;
    }
    
    public void setProduit(Produit p){
        produit = p;
    }
    
    public void setQuantite(int q){
        quantite = q;
    }
    
    @Override
    public String toString(){
        return " Commande n° " + commande.getID()+" : "+ produit.getDenomination()+ " a été commandé pour une quantité de " + quantite; 
    }
}
