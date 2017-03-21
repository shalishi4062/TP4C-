/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

/**
 *
 * @author jcharlesni
 */
@Entity
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public abstract class Livreur implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    protected String adresse;
    protected Double capacite;
    protected boolean disponibilite;
    
    @OneToMany(mappedBy="livreur")
    List<Commande> commandes;

    public Livreur() {
    }
    
    public Livreur(String a, Double c){
        adresse = a;
        capacite = c;
        disponibilite = true;
        commandes = new ArrayList();
    }
     public Long getId() {
        return id;
    }
      public String getAdresse() {
        return adresse;
    }
    
    public Double getCapacite() {
        return capacite;
    }
    
    public boolean getDisponibilite() {
        return disponibilite;
    }
    
    public List<Commande> getCommandes(){
        return commandes;
    }
    
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

     public void setCapacite(double c) {
        this.capacite = c;
    } 
     public void setDisponibilite(boolean c) {
        this.disponibilite = c;
    }
    
     public void addCommande(Commande c){
         commandes.add(c);
     }

     public String toString(){
         return "Livreur :{" + "id=" + id + ", adresse=" + adresse + ", capacité="+ capacite + ", disponibilité="+ disponibilite + '}';
     }
     
    public void livrer(){
        for(int i=0; i<commandes.size(); i++){
            Commande commande = commandes.get(i);
            if(commande.getEtat().equals("En attente") && disponibilite){
               commande.setEtat(1);
               disponibilite = false;
               System.out.println("Votre commande a été livrée au bout de "+ commande.getTimeLivraison()+ " minutes.");
               validerCommande(commande);
            }
        }
    }
     
    public void validerCommande(Commande commande){
        System.out.println(this.getPrenom() +" valide la commande");
        Date now = new Date();
        commande.setDateFin(now);
        commande.setEtat(2);
        System.out.println(commande.getEtat());
        disponibilite = true;
    }
    

    public String getNom() {
        return "lol";
    }

    public String getPrenom() {
        return "lol";
    }

    public String getMail() {
        return "lol";
    }
}
