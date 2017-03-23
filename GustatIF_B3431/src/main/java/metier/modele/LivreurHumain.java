/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 *
 * @author jcharlesni
 */
@Entity
public class LivreurHumain extends Livreur implements Serializable{
    
    private String nom;
    private String prenom;
    private String mail;
    
    protected LivreurHumain(){
        
    }
    
    public LivreurHumain(String a, Double c, String n, String p, String m) {
        super(a, c);
        nom = n;
        prenom = p;
        mail = m;
    }
    
    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getMail() {
        return mail;
    }
    
     public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    
    public String toString(){
         return "Livreur :{" + "id=" + id + ", nom="+ nom + ", prenom="+ prenom + ", mail=" + mail + ", adresse=" + adresse + ", capacité="+ capacite + ", disponibilité="+ disponibilite  +'}';
     }
}
