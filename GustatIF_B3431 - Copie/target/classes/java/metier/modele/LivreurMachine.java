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

/**
 *
 * @author jcharlesni
 */
@Entity
public class LivreurMachine extends Livreur {

    private double vitesse;
    
    protected LivreurMachine(){
        
    }
    
    public LivreurMachine(String a, Double lon, Double lat, Double c, boolean d, double v ){
        super(a, lon, lat, c, d);
        vitesse = v;
    }

    
    public double getVitesse(){
        return vitesse;
    }
    
    public void setVitesse(double v){
        vitesse = v;
    }

    @Override
    public String toString() {
        return "LivreurMachine :{" + "id=" + id + ", adresse=" + adresse + ", longitude=" + longitude + ", latitude=" + latitude + ", vitesse=" + vitesse + ", capacité="+ capacite + ", disponibilité="+ disponibilite + '}';
    }
    
}
