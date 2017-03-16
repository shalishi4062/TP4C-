package metier.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    private String prenom;
    //private String numero;
    private String mail;
    private String adresse;
    private Double longitude;
    private Double latitude;
    
    @OneToMany(mappedBy="client")
    List<Commande> commandes;

    protected Client() {
    }
    
    public Client(String nom, String prenom,/* String numero,*/ String mail, String adresse) {
        this.nom = nom;
        this.prenom = prenom;
        //this.numero = numero;
        this.mail = mail;
        this.adresse = adresse;
        this.longitude = null;
        this.latitude = null;
        commandes = new ArrayList();
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }
    
    /*public String getNumero(){
        return numero;
    }*/

    public String getMail() {
        return mail;
    }

    public String getAdresse() {
        return adresse;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }
    
    public List<Commande> getCommandes(){
        return commandes;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    /*public void setNumero(String numero) {
        this.numero = numero;
    }*/

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setLatitudeLongitude(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    public void addCommande(Commande c){
        commandes.add(c);
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", mail=" + mail + ", adresse=" + adresse + ", longitude=" + longitude + ", latitude=" + latitude + '}';
    }

}
