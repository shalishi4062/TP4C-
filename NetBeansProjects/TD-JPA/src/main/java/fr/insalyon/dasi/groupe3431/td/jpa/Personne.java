
package fr.insalyon.dasi.groupe3431.td.jpa;

import com.google.maps.model.LatLng;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.DATE;

/**
 *
 * @author femezidi
 */
@Entity
public class Personne {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nom;
    private String prenom;
    
    @Temporal(DATE)
    private Date dateNaissance;
    private String adresse;
    private int age;
    private double longitude;
    private double latitude;
    
    public Personne(){
   
    }
    
    public Personne(String n, String p, Date d, int a){
        nom = n;
        prenom = p;
        dateNaissance = d;
        age =a;
    }
    
    
    
    public void setLatLng(LatLng coords){
        latitude = coords.lat;
        longitude = coords.lng;
    }
    
    public void setAdresse(String a){
        adresse = a;
    }
    
    public String toString(){
        return nom + " " + prenom + " a " + age + " ans.";
    }
    
}
