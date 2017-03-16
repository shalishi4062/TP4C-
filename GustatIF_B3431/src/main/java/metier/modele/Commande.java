/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.DATE;

/**
 *
 * @author B431
 */
@Entity
public class Commande implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Temporal(DATE)
    private Date dateDeb;
    @Temporal(DATE)
    private Date dateFin;
    
    @ManyToOne
    private Client client;
    
    @ManyToOne
    private Livreur livreur;
    
    @OneToMany(mappedBy="commande")
    List<Qte_Commande> qte_commande;
    
    private String etat;
    
    protected Commande() {
    }
    
    public Commande(Client c, Date d){
        dateDeb = d;
        client = c;
        livreur = null;
        qte_commande = new ArrayList();
        etat = "En attente";
        dateFin = null;
    }

    
    public long getID(){
        return id;
    }
    
    public Client getClient(){
        return client;
    }
    
    public Livreur getLivreur(){
        return livreur;
    }
    
    public List<Qte_Commande> getQteProduit(){
        return qte_commande;
    }
    
    public void setClient(Client c){
        client = c;
    }
    
    public Date getDateDeb(){
        return dateDeb;
    }
    
    public Date getdateFin(){
        return dateFin;
    }
    
    
    
    public void setDateDeb(Date d){
        dateDeb = d;
    }
    
    public void setdateFin(Date d){
        dateFin = d;
    }
    
    public void setLivreur(Livreur l){
        livreur = l;
    }
    
    public void addQteProduit(Qte_Commande qP){
        qte_commande.add(qP);
    }
    
    public String getEtat(){
        return etat;
    }
    
    public void setEtat(int n){
        switch (n){
            case 0 : etat = "En attente";
                    break;
            case 1 : etat = "En cours";
                    break;
            case 2 : etat = "Finie";
                    break;
            case 3 : etat = "Annulée";
                    break;
            default : System.out.println("Entrez 0, 1, 2, ou 3");
                    break; 
        }
    }
    
    public String getProduitsCommande(){
        String res="Produits commandés : \n";
        double prix = 0;
        List<Qte_Commande> produits = getQteProduit();
        for(int i=0; i<produits.size(); i++){
            Qte_Commande q = produits.get(i);
            res += q.getQuantite() + " x " + q.getProduit().getDenomination() + " au prix unitaire de " + q.getProduit().getPrix() + "\n";
            prix +=  q.getQuantite()* q.getProduit().getPrix();
        }
        res += "Prix total : "+ prix;
        return res;
    }
    
    public double getPoidsTotal(){
        double res = 0.0;
        List<Qte_Commande> produits = getQteProduit();
        for(int i=0; i<produits.size(); i++){
            Qte_Commande q = produits.get(i);
            res +=  q.getQuantite()* q.getProduit().getPoids();
        }
        return res;
    }
    
    public String toString(){
        return "Commande n°"+id+" du client "+ client.getId()+
                " effectuée par le livreur "+ livreur.getId() + " le "+ dateDeb;
    }
}
