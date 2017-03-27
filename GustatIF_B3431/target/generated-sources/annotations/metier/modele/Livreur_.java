package metier.modele;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import metier.modele.Commande;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-03-27T16:32:58")
@StaticMetamodel(Livreur.class)
public abstract class Livreur_ { 

    public static volatile ListAttribute<Livreur, Commande> commandes;
    public static volatile SingularAttribute<Livreur, Double> capacite;
    public static volatile SingularAttribute<Livreur, Boolean> disponibilite;
    public static volatile SingularAttribute<Livreur, Double> latitude;
    public static volatile SingularAttribute<Livreur, String> adresse;
    public static volatile SingularAttribute<Livreur, Long> id;
    public static volatile SingularAttribute<Livreur, Long> version;
    public static volatile SingularAttribute<Livreur, Double> longitude;

}