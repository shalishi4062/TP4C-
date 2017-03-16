/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import metier.modele.Client;
import metier.modele.Commande;
import metier.modele.Livreur;
import metier.modele.LivreurHumain;
import metier.modele.Restaurant;

/**
 *
 * @author jcharlesni
 */
public class ServiceTechnique {
    
    public static void envoiMailInscription(int etat, Client cl){
        //si 0 echec si 1 reussi
        System.out.println("Expediteur : gustatif@gustatif.com\n"
                    + "Pour : " + cl.getMail() +"\n"
                            + "Sujet : Bienvenue chez Gustatif\n"
                            + "Corps :\n"
                            + "Bonjour "+ cl.getPrenom() + ",\n");
        if(etat == 1){
            System.out.println("Nous vous confirmons votre inscription au service Gustat'IF. "
                    + "Votre numero de client est "+ cl.getId() + ".\n");
        }else{
            System.out.println("Votre inscription au service gustat'IF a malheureusement échouée..."
                    + " Merci de recommencer ultériorement.\n");
        }
    }
    public static void envoiMailLivreur(LivreurHumain li, Commande co, Restaurant r){
        //si 0 echec si 1 reussi
        System.out.println("Expediteur : gustatif@gustatif.com\n"
                    + "Pour : " + li.getNom()+ " " + li.getPrenom() + " <" +li.getMail() +">\n"
                            + "Sujet :Livraison n°" + co.getID() + "\n"
                            + "Corps :\n"
                            + "Bonjour "+ li.getPrenom() + ",\n"
                            +"Merci d'effectuer cette livraison dès maintenant tout en respectant le code de la route >.^\n"
                            + "Le Chef\n"
                                    + "Détails de la livraison\n"
                                        + "\t-date/heure :" + co.getdateDeb()+"\n"
                                        + "\t-Livreur : " + li.getPrenom() +" "+li.getNom()+" (n°"+li.getId()+")\n"
                                        + "\t-Restaurant : "+ r.getDenomination()+"\n"
                                        + "\t-Client :\n"+ co.getClient().getPrenom() + " "+co.getClient().getNom()+"\n"
                                                + "\t"+co.getClient().getAdresse()+"\n"
                                                + "\t"+co.getClient().getNumero()+"\n\n"
                                    + "Commande : \n"
                                                        + "\t-a continuer !!!!!!!!!!!!!!!");
                                                

    }
}
