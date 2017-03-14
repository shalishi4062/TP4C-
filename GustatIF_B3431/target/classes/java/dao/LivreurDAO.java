/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Livreur;

public class LivreurDAO {
    
    public void create(Livreur livreur) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            System.out.println(livreur);
            em.persist(livreur);
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public Livreur findById(long id) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Livreur livreur = null;
        try {
            livreur = em.find(Livreur.class, id);
        }
        catch(Exception e) {
            throw e;
        }
        return livreur;
    }
    
    public List<Livreur> findAll() throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Livreur> livreurs = null;
        try {
            Query q = em.createQuery("SELECT c FROM Livreur c");
            livreurs = (List<Livreur>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }
        
        return livreurs;
    }
    
    public void consultList() throws Exception {
        List<Livreur> livreurs = findAll();
        System.out.println("Liste des Livreurs : ");
        for(int i=0; i<livreurs.size(); i++){
            System.out.println(livreurs.get(i).toString());
        }
    }
}
