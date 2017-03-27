/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author B431
 */

@Entity
public class Qte_Commande implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    
    private int quantite;
        
    @ManyToOne
    private Produit produit;
    
    protected Qte_Commande(){
        
    }
    
    public Qte_Commande(Produit p, int q){
        produit = p;
        quantite = q;
    }
   
    
    public Produit getProduit(){
        return produit;
    }
    
    public int getQuantite(){
        return quantite;
    }
    
    public void setProduit(Produit p){
        produit = p;
    }
    
    public void setQuantite(int q){
        quantite = q;
    }
    
    @Override
    public String toString(){
        return  produit.getDenomination()+ " a été commandé pour une quantité de " + quantite; 
    }
}
