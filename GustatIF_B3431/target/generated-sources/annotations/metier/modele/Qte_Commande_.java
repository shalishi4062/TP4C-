package metier.modele;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import metier.modele.Commande;
import metier.modele.Produit;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-03-08T18:00:01")
@StaticMetamodel(Qte_Commande.class)
public class Qte_Commande_ { 

    public static volatile SingularAttribute<Qte_Commande, Long> id;
    public static volatile SingularAttribute<Qte_Commande, Produit> produit;
    public static volatile SingularAttribute<Qte_Commande, Integer> quantite;
    public static volatile SingularAttribute<Qte_Commande, Commande> commande;

}