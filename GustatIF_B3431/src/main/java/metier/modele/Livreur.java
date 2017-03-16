/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import java.util.List;
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
    
    public Livreur(String a, Double c, boolean d ){
        adresse = a;
        capacite = c;
        disponibilite = d;
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
