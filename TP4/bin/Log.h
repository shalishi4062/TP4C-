/*************************************************************************
                           Log  -  Header de la classe Log
                             -------------------
    début                : 10/01/2017
    copyright            : (C) $2017$ par $Julien Charles-Nicolas$
    e-mail               : $julien.charles-nicolas@insa-lyon.fr$
*************************************************************************/

//---------- Interface du module <Log> (Log Log.h) -------------------
#ifndef TP4_Log_H
#define TP4_Log_H

//------------------------------------------------------------------------
// Rôle du module <Log>
// Modélise les lignes du .log en objet Log
//
//------------------------------------------------------------------------

/////////////////////////////////////////////////////////////////  INCLUDE
//--------------------------------------------------- Interfaces utilisées
#include <iostream>
#include <string>
using namespace std;
//------------------------------------------------------------- Constantes

//------------------------------------------------------------------ Types
struct Informations
{   string ip;
    string logname;
    string name;
    string date;
    string fuseau;
    string requete;
    string type;
    string status;
    string data;
    string siteRef;
    string siteCib;
    string referent;
    string navigateur;
    Informations(string i="", string l="", string na="", string d="", string f="", string r="",string t="",string s="", string da="", string re="", string n=""):ip(i) ,logname(l), name(na), date(d), fuseau(f), requete(r), type(t), status(s), data(da), referent(re), navigateur(n){}
};


class Log
{
//----------------------------------------------------------------- PUBLIC

public:
//----------------------------------------------------- Méthodes publiques
     int GetHeure ();
    // Mode d'emploi :
    // Retourne l'heure du log
    // Contrat :
    //

    string GetType ();
    // Mode d'emploi :
    // Retourne le type de fichier de la cible
    // Contrat :
    //

    void splitRefCib();
    // Mode d'emploi :
    // Sépare si nécessaire les sites hébergeures des pages des url de cible et de référent
    // Contrat :
    //


//------------------------------------------------- Surcharge d'opérateurs
    Log & operator = ( const Log & unLog );
    // Mode d'emploi :
    //
    // Contrat :
    //


//-------------------------------------------- Constructeurs - destructeur
    Log ( const Log & unLog );
    // Mode d'emploi (constructeur de copie) :
    //
    // Contrat :
    //

    Log (string c="", Informations i=Informations()): cible(c), Infos(i){};
    // Mode d'emploi :
    // Crée un Log vide
    // Contrat :
    //

    virtual ~Log ( );
    // Mode d'emploi :
    //
    // Contrat :
    //


//------------------------------------------------------------------ PRIVE

protected:
//----------------------------------------------------- Méthodes protégées

private:
//------------------------------------------------------- Méthodes privées

protected:
//----------------------------------------------------- Attributs protégés

private:
//------------------------------------------------------- Attributs privés
    string cible;
    Informations Infos;
//---------------------------------------------------------- Classes amies
    friend istream & operator >> (istream& is, Log& f);
    friend ostream & operator << (ostream& os, Log& f);
    friend class CollectionDeLog;
//-------------------------------------------------------- Classes privées

//----------------------------------------------------------- Types privés

};

//---------------------------------------------- Types dépendants de <Log>


#endif // TP4_Log_H
