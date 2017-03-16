package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Client;

public class ClientDAO {
    
    public void create(Client client) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(client);
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public Client findById(long id) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Client client = null;
        try {
            client = em.find(Client.class, id);
        }
        catch(Exception e) {
            throw e;
        }
        return client;
    }
    
    public List<Client> findAll() throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Client> clients = null;
        try {
            Query q = em.createQuery("SELECT c FROM Client c");
            clients = (List<Client>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }
        
        return clients;
    }
    
    public void consultList() throws Exception {
        List<Client> clients = findAll();
        System.out.println("Liste des clients : ");
        for(int i=0; i<clients.size(); i++){
            System.out.println(clients.get(i).toString());
        }
    }
}
