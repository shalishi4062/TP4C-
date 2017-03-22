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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Version;

/**
 *
 * @author B3431
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Livreur implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    protected String adresse;
    protected Double capacite;
    protected boolean disponibilite;

    @OneToMany(mappedBy = "livreur")
    List<Commande> commandes;
    
    @Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    private long version = 0L;

    public Livreur() {
    }

    public Livreur(String a, Double c) {
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

    public List<Commande> getCommandes() {
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

    public void addCommande(Commande c) {
        commandes.add(c);
    }

    public String toString() {
        return "Livreur :{" + "id=" + id + ", adresse=" + adresse + ", capacité=" + capacite + ", disponibilité=" + disponibilite + '}';
    }

    public void prendreCommande() {
        for (int i = 0; i < commandes.size(); i++) {
            Commande commande = commandes.get(i);
            if (commande.getEtat().equals("En attente")) {
                commande.setEtat(1);
                disponibilite = false;
                System.out.println("Votre commande sera livrée au bout de " + commande.getTimeLivraison() + " minutes)");
                if (commande.getTimeLivraison() < 3.0) {
                    System.out.println("Votre commande sera bientôt livreé...");
                    Date now = new Date();
                    Date end = new Date((long) (now.getTime() + (commande.getTimeLivraison() * 60000 )));
                    while (now.before(end)) {
                      now = new Date();
                    }
                    validerCommande(commande);
                }
                break;
            }
        }
    }

    public void validerCommande(Commande commande) {
        System.out.println(this.getPrenom() + " valide la commande");
        Date now = new Date();
        commande.setDateFin(now);
        commande.setEtat(2);
        System.out.println(commande.getEtat());
        disponibilite = true;
    }

    public double getVitesse() {
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
}
