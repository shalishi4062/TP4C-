/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import dao.JpaUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import metier.modele.Client;
import metier.modele.Commande;
import metier.modele.Livreur;
import metier.modele.LivreurHumain;
import metier.modele.LivreurMachine;
import metier.modele.Produit;
import metier.modele.Qte_Commande;
import metier.modele.Restaurant;
import metier.service.ServiceMetier;
import static util.Saisie.lireChaine;
import static util.Saisie.lireInteger;

/**
 *
 * @author B3431
 */
public class Main {

    static ServiceMetier smetier = new ServiceMetier();

    public static void main(String[] args) throws Exception {

        JpaUtil.init();

        //smetier.createLivreurs(); //a mettre que si pas de livreurs
        System.out.println("Bienvenue sur l'application GustatIF ! ");

        choixIdentite();

        JpaUtil.destroy();
    }

    public static int choixIdentite() {

        Integer j = null;
        while (j == null) {
            j = lireInteger("Etes vous livreur(0) ou gestionnaire(1) ou client(2) ?", Arrays.asList(0, 1, 2));
        }
        switch (j) {
            case 0:
                testLivreur();
                break;
            case 1:
                testGestionnaire();
                break;
            case 2:
                testClient();
                break;
        }
        return j;
    }

    //Fonctions Console
    //Livreur
    public static void testLivreur() {

        System.out.println("Bienvenue livreur de l'application GustatIF !");

        List<Livreur> livreurs = smetier.getLivreurs();
        List<Integer> l = new ArrayList();
        for (int i = 0; i < livreurs.size(); i++) {
            if (livreurs.get(i) instanceof LivreurHumain) {
                System.out.println("(" + i + ")" + livreurs.get(i).getNom());
                l.add(i);
            }
        }
        Integer s = null;
        while (s == null) {
            s = lireInteger("Qui êtes vous ? #Entrez le numero correspondant: ", l);
        }
        Livreur livreur = livreurs.get(s);
        Integer f = null;

        if (!livreur.getCommandes().isEmpty()) {
            Commande coli = livreur.getCommandes().get(livreur.getCommandes().size() - 1);
            if (coli.getEtat().equals("En cours")) {
                while (f == null) {
                    f = lireInteger("Avez vous fini cette commande ? oui(1)\n" + coli, Arrays.asList(0, 1));
                }
                if (f == 1) {
                    smetier.finirCommande(livreur.getCommandes().get(livreur.getCommandes().size() - 1), livreur);
                }
            } else {
                System.out.println("Vous n'avez aucune commande en cours...");
            }

        } else {
            System.out.println("Vous n'avez aucune commande...");
        }
    }

    public static void testGestionnaire() {

        System.out.println("Bienvenue administrateur de l'application GustatIF !");

        Integer i = null;

        while (i == null || i!= 0) {
            i = lireInteger("Voulez vous finir une commande d'un drone(1), voir la belle carte(2) ou quitter l'application(0) ?", Arrays.asList(0, 1, 2));
        

        switch (i) {
            case 1:
                List<Livreur> livreurs = smetier.getLivreurs();
                List<Integer> l = new ArrayList();
                for (int h = 0; h < livreurs.size(); h++) {
                    if (livreurs.get(h) instanceof LivreurMachine) {
                        System.out.println("(" + h + ")" + livreurs.get(h).getNom());
                        l.add(h);
                    }
                }
                Integer s = null;
                while (s == null) {
                    s = lireInteger("Quel drone ? #Entrez le numero correspondant: ", l);
                }
                Livreur livreur = livreurs.get(s);
                Integer f = null;

                if (!livreur.getCommandes().isEmpty()) {
                    Commande coli = livreur.getCommandes().get(livreur.getCommandes().size() - 1);
                    if (coli.getEtat().equals("En cours")) {
                        while (f == null) {
                            f = lireInteger("Avez vous fini cette commande ? oui(1)\n" + coli, Arrays.asList(0, 1));
                        }
                        if (f == 1) {
                            smetier.finirCommande(livreur.getCommandes().get(livreur.getCommandes().size() - 1), livreur);
                        }
                    } else {
                        System.out.println("Vous n'avez aucune commande en cours...");
                    }

                } else {
                    System.out.println("Vous n'avez aucune commande...");
                }
                i = null;
                break;
            case 2:
                i = null;
                while (i == null || i != 0) {
                    i = lireInteger("Souhaitez vous voir tous les restaurants (1), livreurs (2),\n "
                            + "clients (3), livraisons en cours (4) ou quitter (0) ?", Arrays.asList(0, 1, 2, 3, 4));

                    switch (i) {
                        case 1:
                            visualisationRestaurants();
                            break;
                        case 2:
                            visualisationLivreurs();
                            break;
                        case 3:
                            visualisationClients();
                            break;
                        case 4:
                            visualisationCommandesEnCours();
                            break;
                        default:
                            break;
                    }
                    break;
                }
        }
        }
    }

    public static void visualisationRestaurants() {
        List<Restaurant> restaurants = smetier.getRestaurants();
        for (int i = 0; i < restaurants.size(); i++) {
            System.out.println("(" + i + ")" + restaurants.get(i).getDenomination()
                    + " Lattitude : " + restaurants.get(i).getLatitude() + " Longitude : " + restaurants.get(i).getLongitude());
        }
    }

    public static void visualisationLivreurs() {
        List<Livreur> livreurs = smetier.getLivreurs();
        for (int i = 0; i < livreurs.size(); i++) {
            System.out.println("(" + i + ")" + livreurs.get(i).getNom()
                    + " Lattitude : " + livreurs.get(i).getLatitude() + " Longitude : " + livreurs.get(i).getLongitude());
        }
    }

    public static void visualisationClients() {
        List<Client> clients = smetier.getClients();
        for (int i = 0; i < clients.size(); i++) {
            System.out.println("(" + i + ")" + clients.get(i).getPrenom() + " " + clients.get(i).getNom()
                    + " Lattitude : " + clients.get(i).getLatitude() + " Longitude : " + clients.get(i).getLongitude());
        }
    }

    public static void visualisationCommandesEnCours() {
        List<Commande> commandes = smetier.getCommandes();
        for (int i = 0; i < commandes.size(); i++) {
            if (commandes.get(i).getEtat().equals("En cours")) {
                System.out.println("Clients : " + commandes.get(i).getClient() + " Restaurant : " + commandes.get(i).getRestaurant()
                        + " heure de debut : " + commandes.get(i).getDateDeb());
            }
        }
    }

    //Client
    public static void testClient() {
        Client client = null;

        System.out.println("Bienvenue client de l'application GustatIF !");

        Integer n = lireInteger("Souhaitez vous vous connecter(1) ou vous inscrire(2) ?", Arrays.asList(1, 2));
        while (n != 1 && n != 2) {
            n = lireInteger("Souhaitez vous vous connecter(1) ou vous inscrire(2) ?", Arrays.asList(1, 2));
        }

        if (n == 1) {
            client = signIn();
            while (client == null) {
                Integer i = lireInteger(" Voulez vous retaper votre adresse mail (1) ou vous vous inscrire (2) ?", Arrays.asList(1, 2));
                if (i == 1 && i != null) {
                    client = signIn();
                } else if (i != null) {
                    client = signUp();
                }
            }
        } else {
            client = signUp();
        }

        System.out.println("Vous êtes connectés !");
        System.out.println("Bienvenue " + client.getPrenom());
        n = -1;
        while (n != 0) {
            n = -1;
            n = lireInteger("Voulez vous effectuer une commande (1) ou quitter l'application (0) ?", Arrays.asList(0, 1));
            if (n == 1) {
                createCommande(client);
            }
        }

    }

    public static Client signIn() {
        System.out.println("Afin de vous connecter..");
        String mail = lireChaine("Veuillez taper le mail avec lequel vous vous êtes inscrit :");
        return smetier.singInClient(mail);
    }

    public static Client signUp() {

        System.out.println("Bienvenue sur les services de l'application GustatIF !");
        System.out.println("Veuillez taper..");
        String nom = lireChaine("Votre nom :");
        String prenom = lireChaine("Votre prénom :");
        String mail = lireChaine("Votre mail :");
        String adresse = lireChaine("Votre adresse :");
        Client cl = new Client(nom, prenom, mail, adresse);
        return smetier.signUpClient(cl);
    }

    public static void createCommande(Client client) {

        int x = 2;
        List<Restaurant> restaurants = smetier.getRestaurants();
        Restaurant restaurant = selectRestaurant(restaurants);
        Date date = selectDate();
        Commande commande = new Commande(client, date, restaurant);
        //smetier.createCommande(commande);
        while (x != 0) {
            Qte_Commande qcommande = selectProduit(restaurant, commande);
            commande.addQteProduit(qcommande);
            x = lireInteger("Souhaitez vous un autre produit(1) ou ce sera tout(0) ? "
                    + "#Tapez le chiffre entre parenthèse lié à votre choix");
        }
        System.out.println("Hello ! ");
        smetier.checkCommande(commande, restaurant);
    }

    public static Restaurant selectRestaurant(List<Restaurant> restaurants) {
        Integer choix = null;
        List<Integer> l = new ArrayList<>();
        for (Integer j = 1; j <= restaurants.size(); j++) {
            l.add(j);
        }
        System.out.println("Choisissez un restaurant parmi ceux ci :");
        for (int i = 1; i <= restaurants.size(); i++) {
            Restaurant resto = restaurants.get(i - 1);
            System.out.println("(" + i + ")" + resto.getDenomination() + " : " + resto.getDescription());
        }
        while (choix == null) {
            choix = lireInteger("Tapez le numéro du restaurant choisi.", l);
        }
        return restaurants.get(choix - 1);
    }

    public static Qte_Commande selectProduit(Restaurant restaurant, Commande commande) {
        Integer p = null;
        int quantite;
        int x = 0;
        System.out.println("Voici la liste des produits disponibles dans le restaurant " + restaurant.getDenomination() + " : ");
        List<Produit> produits = restaurant.getProduits();
        for (int i = 1; i <= produits.size(); i++) {
            Produit produit = produits.get(i - 1);
            System.out.println("(" + i + ")" + produit.getDenomination() + " : " + produit.getDescription());
        }
        List<Integer> l = new ArrayList<>();
        for (Integer j = 1; j <= produits.size(); j++) {
            l.add(j);
        }
        while (p == null) {
            p = lireInteger("Tapez le numero du produit que vous souhaitez.", l);
        }
        quantite = lireInteger("Quelle quantité ?");
        Qte_Commande qcommande = new Qte_Commande( produits.get(p - 1), quantite);
        //smetier.createQteCommande(qcommande);
        return qcommande;
    }

    public static Date selectDate() {
        Date date;
        Integer choix = null;
        while (choix == null) {
            choix = lireInteger("Souhaitez vous être livré maintenant(1) ou à une date précise(2) ?", Arrays.asList(1, 2));
        }
        date = new Date();
        String res = "";
        if (choix != 1) {
            while (res.equals("")) {
                res = lireChaine("A quelle heure souhaitez vous être livré ? (XXhXX)");
            }
            int heure = Integer.parseInt(res.substring(0, 1));
            int minute = Integer.parseInt(res.substring(3));
            date.setHours(heure);
            date.setMinutes(minute);
        }
        return date;
    }

}
