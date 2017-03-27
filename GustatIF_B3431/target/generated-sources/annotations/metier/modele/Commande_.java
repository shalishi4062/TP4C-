package metier.modele;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import metier.modele.Client;
import metier.modele.Livreur;
import metier.modele.Qte_Commande;
import metier.modele.Restaurant;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-03-27T16:32:58")
@StaticMetamodel(Commande.class)
public class Commande_ { 

    public static volatile SingularAttribute<Commande, Date> dateDeb;
    public static volatile SingularAttribute<Commande, Restaurant> restaurant;
    public static volatile ListAttribute<Commande, Qte_Commande> qte_commande;
    public static volatile SingularAttribute<Commande, Client> client;
    public static volatile SingularAttribute<Commande, Long> id;
    public static volatile SingularAttribute<Commande, Date> dateFin;
    public static volatile SingularAttribute<Commande, Livreur> livreur;
    public static volatile SingularAttribute<Commande, String> etat;

}