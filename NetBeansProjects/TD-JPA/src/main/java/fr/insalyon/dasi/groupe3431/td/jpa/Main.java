package fr.insalyon.dasi.groupe3431.td.jpa;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.*;

/**
 *
 * @author femezidi
 */
public class Main {
    
    final static GeoApiContext MON_CONTEXTE_GEOAPI = new GeoApiContext().setApiKey("AIzaSyAhf3JleYpal9S-xouJYH8lf7Dvz5Y2Nko");

    public static LatLng getLatLng(String adresse) {
        try {
            GeocodingResult[] results = GeocodingApi.geocode(MON_CONTEXTE_GEOAPI, adresse).await();
            return results[0].geometry.location;
        } catch (Exception ex) {
            return null;
        }
    }

    public static void main(String[] args) {

        //Gestionnaire d'entité
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("appPU");
        EntityManager em = emf.createEntityManager();

        //Initialisation d'une Personne
        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
        String dateN = "15-11-96";
        try {
            date = dateFormat.parse(dateN);
        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        em.persist(new Personne("Mezidi", "Fatima", date, 20));

        try {
            date = dateFormat.parse("05-11-96");
        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        em.persist(new Personne("Charles-Nicolas", "Julien", date, 20));

        Integer i = 1;
        while (em.find(Personne.class, i) != null) {
            System.out.println(em.find(Personne.class, i));
            i++;
        }

        //Fermeture
        em.close();

    }

}
