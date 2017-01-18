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
};

typedef map <string,dessin> mapGraph; //cibleref, structure inforamtions
typedef map <string, int> mapTopDix; //cible, compteur

enum TypeExclus
{   js,
    css,
    ico,
    ics,
    png,
    jpg,
    gif
};

const map<string, TypeExclus> exclus = {{"js", js}, {"css", css}, {"ico", ico}, {"ics", ics}, {"png", png}, {"jpg", jpg}, {"gif", gif}};
//------------------------------------------------------------------------
// Rôle de la classe <CollectionDeLog>
//
//
//------------------------------------------------------------------------

class CollectionDeLog
{
//----------------------------------------------------------------- PUBLIC

public:
//----------------------------------------------------- Méthodes publiques
    // type Méthode ( liste des paramètres );
    // Mode d'emploi :
    //
    // Contrat :
    //


//------------------------------------------------- Surcharge d'opérateurs

//-------------------------------------------- Constructeurs - destructeur

    CollectionDeLog ( );
    // Mode d'emploi :
    //
    // Contrat :
    //

    CollectionDeLog (const CollectionDeLog & unCollectionDeLog );
    // Mode d'emploi (constructeur de copie) :
    //
    // Contrat :char e='n'
    //

    // nom du fichier, exlusion (e ou n), heure (uint<12 ou -1), si topdix (o ou g), nomdugraph
    CollectionDeLog (string nf, char e = 'n', int heure = -1, char topdix = 'o', string nfGraph = "unnamed.dot");

    virtual ~CollectionDeLog ( );
    // Mode d'emploi :
    //
    // Contrat :
    //


    void RemplirMapTopDix ();

    void RemplirMapGraph ();

    void AfficherTopDix ();
    void EnregistrerGraph (string nfGraph);

//------------------------------------------------------------------ PRIVE



private:
//----------------------------------------------------- Méthodes protégées

//----------------------------------------------------- Attributs protégés

    bool exclusion;
    int heure;

    mapTopDix myMapTopDix;
    mapGraph myMapGraph;
    string nomFichier;
    friend class Log;

};

//-------------------------------- Autres définitions dépendantes de <CollectionDeLog>

#endif // CollectionDeLog_H
