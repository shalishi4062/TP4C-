/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Qte_Commande;

public class Qte_CommandeDAO {
    
    public void create(Qte_Commande qte_Commande) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(qte_Commande);
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public Qte_Commande findById(long id) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Qte_Commande qte_Commande = null;
        try {
            qte_Commande = em.find(Qte_Commande.class, id);
        }
        catch(Exception e) {
            throw e;
        }
        return qte_Commande;
    }
    
    public List<Qte_Commande> findAll() throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Qte_Commande> qte_Commandes = null;
        try {
            Query q = em.createQuery("SELECT c FROM Qte_Commande c");
            qte_Commandes = (List<Qte_Commande>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }
        
        return qte_Commandes;
    }
    
    public void consultList() throws Exception {
        List<Qte_Commande> qte_Commandes = findAll();
        System.out.println("Liste des Qte_Commandes : ");
        for(int i=0; i<qte_Commandes.size(); i++){
            System.out.println(qte_Commandes.get(i).toString());
        }
    }
}
