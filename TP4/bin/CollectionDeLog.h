/*************************************************************************
                           CollectionDeLog  -  Header de la classe CollectionDeLog
                             -------------------
    début                : ??/??/2017
    copyright            : (C) 2017 par Julien Charles-Nicolas,
					Shali Shi, 
					Fatima-Ezzahra Mezidi
*************************************************************************/

//---------- Interface de la classe <CollectionDeLog> (fichier CollectionDeLog.h) ----------------
#if ! defined ( CollectionDeLog_H )
#define CollectionDeLog_H

//--------------------------------------------------- Interfaces utilisées
#include<string>
#include<map>
#include <iostream>
#include <fstream>
#include <set>
#include "Log.h"

using namespace std;

//------------------------------------------------------------- Constantes

//------------------------------------------------------------------ Types
struct dessin{
    int compteur;
    string cible;
    string referent;
}; // structure aidant pour la formation du fichier .dot


typedef map <string,dessin> mapGraph; //cibleref, structure dessin; utilise lors de la creation du .dot
typedef map <string, int> mapTopDix; //cible, compteur; utilise lors de la creation du top 10

enum TypeExclus
{   js,
    css,
    ico,
    ics,
    png,
    jpg,
    gif
}; //tous les types exclus lors du filtrage (commande -e)

const map<string, TypeExclus> exclus = {{"js", js}, {"css", css}, {"ico", ico}, {"ics", ics}, {"png", png}, {"jpg", jpg}, {"gif", gif}};
//map contenant les types exclus


//------------------------------------------------------------------------
// Rôle de la classe <CollectionDeLog>
// C'est l'objet du top 10 ou du graph selon comment il est créé
//Il lit le fichier avec les log et le traduit en top dix et en graph
//------------------------------------------------------------------------

class CollectionDeLog
{
//----------------------------------------------------------------- PUBLIC

public:
//----------------------------------------------------- Méthodes publiques

//------------------------------------------------- Surcharge d'opérateurs

//-------------------------------------------- Constructeurs - destructeur

    CollectionDeLog ( );
    // Mode d'emploi : constructeur par defaut
    // ne construit rien

    CollectionDeLog (const CollectionDeLog & unCollectionDeLog );
    // Mode d'emploi (constructeur de copie) :
    // inutile -> a supprimer ???
    // Contrat :
    //

    CollectionDeLog (string nf, char e = 'n', int heure = -1, char topdix = 'o', string nfGraph = "unnamed.dot");
    // Mode d'emploi : constructeur de la classe
    // prend en parametres le fichier .log
    // et toutes les conditions pour filtrer ce fichier
    // ainsi que les attributs du fichier à creer s'il le faut
    // Contrat :nom du fichier, exlusion (e ou n), 
    // heure (uint<12 ou -1), si topdix (o ou g), nomdugraph

    virtual ~CollectionDeLog ( );
    // Mode d'emploi : destructeur de CollectionDeLog
    //

    void RemplirMapTopDix ();
    // Mode d'emploi : rempli la structure de donnees contenant les informations
    // interessantes du fichier contenant les logs pour pouvoir faire le top 10

    void RemplirMapGraph ();
    // Mode d'emploi : rempli la structure de donnees contenant les informations
    // interessantes du fichier contenant les logs pour pouvoir faire le graph

    void AfficherTopDix ();
    // Mode d'emploi : affiche le top 10 dans la sortie standard
    //

    void EnregistrerGraph (string nfGraph);
    // Mode d'emploi : crée le fichier permettant de dessiner le top
    //
    // Contrat : nfGraph est le nom du fichier qui va etre cree
    //

//------------------------------------------------------------------ PRIVE



private:
//----------------------------------------------------- Méthodes protégées

//----------------------------------------------------- Attributs protégés

    bool exclusion; // exclure les fichiers speciaux
    int heure; // garder que les logs de cette heure

    mapTopDix myMapTopDix; //structure contenant tous les logs et leur nombre de hit
    mapGraph myMapGraph; //structure contenant les cibles et leur referant avec le nombre de fois
    string nomFichier;// nom du fichier contenant les logs
    friend class Log;

};

//-------------------------------- Autres définitions dépendantes de <CollectionDeLog>

#endif // CollectionDeLog_H
