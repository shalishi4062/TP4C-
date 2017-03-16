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
    
    protected Commande() {
    }
    
    public Commande(Client c, Livreur l, Date d){
        dateDeb = d;
        client = c;
        livreur = l;
        qte_commande = new ArrayList();
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
    
    public Date getdateDeb(){
        return dateDeb;
    }
    
    public Date getdateFin(){
        return dateFin;
    }
    
    public List<Qte_Commande> getQteProduit(){
        return qte_commande;
    }
    
    public void setClient(Client c){
        client = c;
    }
    
    public void setLivreur(Livreur l){
        livreur = l;
    }
    
     public void setdateFin(Date d){
        dateFin = d;
    }
    
    public void addQteProduit(Qte_Commande qP){
        qte_commande.add(qP);
    }
    
    public String toString(){
        return "Commande n°"+id+" du client "+ client.getId()+
                " effectuée par le livreur "+ livreur.getId() + " le "+ dateDeb;
    }
    
}
