/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author B3431
 */
@Entity
public class LivreurMachine extends Livreur implements Serializable{
    
    private String denomination;

    private double vitesse;
    
    protected LivreurMachine(){
        
    }
    
    public LivreurMachine(String a, Double c, String den, double v ){
        super(a, c);
        denomination = den;
        vitesse = v;
    }
    
    public String getDenomination(){
        return denomination;
    }
    
    public void setDenomination(String d){
        denomination = d;
    }

    
    public double getVitesse(){
        return vitesse;
    }
    
    public void setVitesse(double v){
        vitesse = v;
    }

    @Override
    public String toString() {
        return "LivreurMachine :{" + "id=" + id + ", adresse=" + adresse + ", vitesse=" + vitesse + ", capacité="+ capacite + ", disponibilité="+ disponibilite + '}';
    }
    
    public String getNom(){
        return getDenomination();
    }
    
}
