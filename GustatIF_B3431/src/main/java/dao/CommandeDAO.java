/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Commande;

public class CommandeDAO {
    
    public void create(Commande commande) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(commande);
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public Commande findById(long id) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Commande commande = null;
        try {
            commande = em.find(Commande.class, id);
        }
        catch(Exception e) {
            throw e;
        }
        return commande;
    }
    
    public List<Commande> findAll() throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Commande> commandes = null;
        try {
            Query q = em.createQuery("SELECT c FROM Commande c");
            commandes = (List<Commande>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }
        return commandes;
    }
    
    public List<Commande> findAllByClientID(long id) throws Exception{
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Commande> commandes = null;
        try {
            Query q = em.createQuery("SELECT co FROM Commande co WHERE co.CLIENT_ID ="+id);
            commandes = (List<Commande>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }
        return commandes;
    }
    
    public void consultList() throws Exception {
        List<Commande> commandes = findAll();
        System.out.println("Liste des Commandes : ");
        for(int i=0; i<commandes.size(); i++){
            System.out.println(commandes.get(i).toString());
        }
    }
}
