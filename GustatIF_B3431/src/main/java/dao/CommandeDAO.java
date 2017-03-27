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
import metier.modele.Qte_Commande;

/**
 *
 * @author B431
 */
public class CommandeDAO {

    public void create(Commande commande) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            System.out.println();
            System.out.println("Hello !!! It's me ");
            System.out.println();
            em.persist(commande);
        } catch (Exception e) {
            throw e;
        }
    }

    public void update(Commande commande) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.merge(commande);
        } catch (Exception e) {
            throw e;
        }
    }

    public void delete(Commande commande) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.remove(commande);
        } catch (Exception e) {
            throw e;
        }
    }

    public void cancelById(long id) throws Exception {

    }

    public String getProduitsCommandeById(long id) throws Exception {
        String res = "Produits commandés : \n";
        double prix = 0;
        Commande commande = findById(id);
        List<Qte_Commande> produits = commande.getQteProduit();
        for (int i = 0; i < produits.size(); i++) {
            Qte_Commande q = produits.get(i);
            res += q.getQuantite() + " x " + q.getProduit().getDenomination() + " au prix unitaire de " + q.getProduit().getPrix() + "\n";
            prix += q.getQuantite() * q.getProduit().getPrix();
        }
        res += "Prix total : " + prix;
        return res;
    }

    public Commande findById(long id) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Commande commande = null;
        try {
            commande = em.find(Commande.class, id);
        } catch (Exception e) {
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
        } catch (Exception e) {
            throw e;
        }
        return commandes;
    }

    public List<Commande> findAllByClientID(long id) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Commande> commandes = null;
        try {
            Query q = em.createQuery("SELECT co FROM Commande co WHERE co.id = :id");
            q.setParameter("id", id);
            commandes = (List<Commande>) q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return commandes;
    }

    public void consultList() throws Exception {
        List<Commande> commandes = findAll();
        System.out.println("Liste des Commandes : ");
        for (int i = 0; i < commandes.size(); i++) {
            System.out.println(commandes.get(i).toString());
        }
    }
}
