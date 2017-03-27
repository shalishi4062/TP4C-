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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.persistence.Version;
import static util.GeoTest.getLatLng;

/**
 *
 * @author jcharlesni
 */
@Entity
@Inheritance(strategy= InheritanceType.JOINED)
public abstract class Livreur implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    protected String adresse;
    protected Double capacite;
    protected boolean disponibilite;
    
    @OneToMany(mappedBy="livreur")
    List<Commande> commandes;
    
    @Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    private long version = 0L;
    
    //ou en Transient pour ne pas les persister ??
    private Double longitude;
    private Double latitude;

    public Livreur() {
    }
    
    public Livreur(String a, Double c){
        adresse = a;
        capacite = c;
        disponibilite = true;
        commandes = new ArrayList();
        this.longitude = getLatLng(a).lng;
        this.latitude = getLatLng(a).lat;
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
        this.latitude = getLatLng(adresse).lng;
        this.longitude = getLatLng(adresse).lat;
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
               System.out.println("Votre commande a été livrée au bout de "+ commande.getTimeLivraison(this)+ " minutes.");
               finirCommande(commande);
            }
        }
    }
     
    public void prendreCommande() {
        for (int i = 0; i < commandes.size(); i++) {
            Commande commande = commandes.get(i);
            if (commande.getEtat().equals("En cours") && disponibilite) {
                //commande.setEtat(1);
                //disponibilite = false;
                System.out.println("Votre commande sera livrée au bout de " + commande.getTimeLivraison(this) + " minutes)");
                if (commande.getTimeLivraison(this) < 3.0) {
                    System.out.println("Votre commande sera bientôt livrée...");
                    Date now = new Date();
                    Date end = new Date((long) (now.getTime() + (commande.getTimeLivraison(this) * 60000 )));
                    while (now.before(end)) {
                      now = new Date();
                    }
                    finirCommande(commande);
                }
                break;
            }
        }
    }

    public void finirCommande(Commande commande) {
        System.out.println(this.getPrenom() + " valide la commande");
        Date now = new Date();
        commande.setDateFin(now);
        commande.setEtat(2);
        System.out.println(commande.getEtat());
        disponibilite = true;
    }
    
    public double getVitesse(){
        return 1.0;
    }
    

    public String getNom() {
        return "";
    }

    public String getPrenom() {
        return "";
    }

    public String getMail() {
        return "";
    }
    
    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }
}
