/*************************************************************************
                           CollectionDeLog  -  description
                             -------------------
    début                : 10 janv. 2017
    copyright            : (C) 2017 par jcharlesni
 *************************************************************************/

//---------- Réalisation du module <CollectionDeLog> (fichier CollectionDeLog.cpp) -----

/////////////////////////////////////////////////////////////////  INCLUDE
//-------------------------------------------------------- Include système

//------------------------------------------------------ Include personnel

#include "CollectionDeLog.h"

///////////////////////////////////////////////////////////////////  PRIVE
//------------------------------------------------------------- Constantes

//------------------------------------------------------------------ Types

//---------------------------------------------------- Variables statiques

//------------------------------------------------------ Fonctions privées

//////////////////////////////////////////////////////////////////  PUBLIC
//---------------------------------------------------- Fonctions publiques


CollectionDeLog::CollectionDeLog ( ){
#ifdef MAP
    cout << "Appel au constructeur de <CollectionDeLog>" << endl;
#endif
}

CollectionDeLog::CollectionDeLog (const CollectionDeLog & unCollectionDeLog){
#ifdef MAP
    cout << "Appel au constructeur de <CollectionDeLog>" << endl;
#endif
}


CollectionDeLog::CollectionDeLog(string nf, char e, int h, char topdix, string nfGraph) {
#ifdef MAP
    cout << "Appel au constructeur de <CollectionDeLog>" << endl;
#endif

    nomFichier = nf;
    if (e == 'e'){
        exclusion = true;
    }else{
        exclusion = false;
    }
    heure = h;

    if (topdix == 'o'){
        AfficherTopDix();
    }else if (topdix == 'g'){
        EnregistrerGraph(nfGraph);
    }

}


CollectionDeLog::~CollectionDeLog() {
#ifdef MAP
    cout << "Appel au destructeur de <CollectionDeLog>" << endl;
#endif
}

void CollectionDeLog::RemplirMapTopDix(){
#ifdef MAP
    cout << "Appel de AjouterTopDix de <CollectionDeLog>" << endl;
#endif
    if(heure!=-1) cout << "Warning : only hits between " << heure << "h and "<< heure+1 << "h have been taken into account" << endl;
    Log l;

    ifstream file ( nomFichier.c_str() );
    if(file.good()){
        while(file >> l){
            //verification
            l.splitRefCib();
            int s = 0;
            if(isdigit(l.Infos.status[0])) s= stoi(l.Infos.status);
            if(s==200) {
                if (((exclusion && exclus.find(l.GetType()) == exclus.end()) &&
                     (heure != -1 && l.GetHeure() == heure)) || (!exclusion && heure == -1) ||
                    (!exclusion && heure != -1 && l.GetHeure() == heure) || (heure == -1 && (exclusion &&
                                                                                             exclus.find(
                                                                                                     l.GetType()) ==
                                                                                             exclus.end()))) { // j'ai pas mis les extensions -> faire collection
                    //verification de l'existence de la cible
                    if (myMapTopDix.size() != 0 && myMapTopDix.find(l.cible) != myMapTopDix.end()) {
                        myMapTopDix[l.cible] += 1;
                    } else {
                        if (!l.cible.empty()) {
                            myMapTopDix.insert(make_pair(l.cible, 1));
                        }
                    }
                }
            }
        }
    }

}

void CollectionDeLog::RemplirMapGraph() {
#ifdef MAP
    cout << "Appel de RemplirMapGraph de <CollectionDeLog>" << endl;
#endif

    Log l;
    string cibref;
    dessin dessi;

    ifstream file ( nomFichier.c_str() );
    if(file.good()){
        while(file >> l){
            //verification
            int s = 0;
            if(isdigit(l.Infos.status[0])) s= stoi(l.Infos.status);
            if(s==200) {
                if (((exclusion && exclus.find(l.GetType()) == exclus.end()) &&
                     (heure != -1 && l.GetHeure() == heure)) || (!exclusion && heure == -1) ||
                    (!exclusion && heure != -1 && l.GetHeure() == heure) || (heure == -1 && (exclusion &&
                                                                                             exclus.find(
                                                                                                     l.GetType()) ==
                                                                                             exclus.end()))) {
                    cibref = l.cible + l.Infos.referent; //clé de la map

                    //verification de l'existence de la cible
                    if (myMapGraph.size() != 0 && myMapGraph.find(cibref) != myMapGraph.end()) {
                        myMapGraph[cibref].compteur += 1;
                    } else {

                        if (!l.cible.empty()) {
                            dessi.compteur = 1;
                            dessi.cible = "\"" + l.cible +"\"";
                            if(dessi.cible.find("http://")!= dessi.cible.npos) {
                                size_t pos = dessi.cible.find(".fr");
                                dessi.cible = "\""+ dessi.cible.substr(pos+3);
                            }
                            dessi.referent = l.Infos.referent; //contient le nombre de fois que referent -> cible a été effectué
                            if(dessi.referent.find("http://")!= dessi.referent.npos) {
                                size_t pos = dessi.referent.find(".fr");
                                dessi.referent = "\""+ dessi.referent.substr(pos+3);
                            }
                            myMapGraph.insert(make_pair(cibref, dessi));
                        }

                    }
                }
            }
        }
    }
}

void CollectionDeLog::AfficherTopDix(){
#ifdef MAP
    cout << "Appel de AfficherTopDix de <CollectionDeLog>" << endl;
#endif

    RemplirMapTopDix();

    Log l;

    typedef multimap <int, string> setTopDix;
    setTopDix mySetTopDix;

    mapTopDix::const_iterator itmap;

    for(itmap = myMapTopDix.begin(); itmap != myMapTopDix.end(); ++itmap){
        mySetTopDix.insert(make_pair(itmap->second, itmap->first));
    }

    int compt =0;
    setTopDix::const_iterator itset;

    for(itset = mySetTopDix.end() ; compt <11 && itset !=mySetTopDix.begin(); --itset){
        if(itset!=mySetTopDix.end()) {
            cout << itset->second << " (" << itset->first << " hits)" << endl;
        }
        compt++;

    }
    if(compt<11) cout << itset->second << "  (" << itset->first << " hits)" << endl; // ca devrait pas etre 10

}

void CollectionDeLog::EnregistrerGraph(string nfGraph){
#ifdef MAP
    cout << "Appel de EnregistrerGraph de <CollectionDeLog>" << endl;
#endif

    cout << "Dot-file " << nfGraph << " generated" << endl;
    AfficherTopDix();
    RemplirMapGraph();

    typedef map <string, int> mapNode; //elle contiendra le nom d'une page (cible ou referent) et le numero de node associe
    mapNode myMapNode;

    int nombreNode = 0;

    mapTopDix::const_iterator itmap;

    dessin dessi;

    ofstream ouf;
    ouf.open(nfGraph, ios::trunc);
    if(ouf.good())
    {
        ouf << "digraph {" << endl;

        //on associe toutes les cibles et tous les referents à un numero de node
        //et on l'ecrit sur le fichier
        for(auto itmap = myMapGraph.begin(); itmap != myMapGraph.end(); ++itmap){

            dessi = itmap->second;
            // on verifie si on a deja dessine un node de la cible
            if (myMapNode.find(dessi.cible) == myMapNode.end()) {
                myMapNode.insert(make_pair(dessi.cible, nombreNode));
                ouf << "node" << nombreNode << " [label=" << dessi.cible << "];" << endl;
                nombreNode+=1;
            }
            // on verifie si on a deja dessine un node du referent
            if (myMapNode.find(dessi.referent) == myMapNode.end() ) {
                myMapNode.insert(make_pair(dessi.referent, nombreNode));
                ouf << "node" << nombreNode << " [label=" << dessi.referent << "];" << endl;
                nombreNode+=1;
            }

        }

        //on parcourt la map cette fois pour enregistrer les connexions entre les nodes
        for(auto itmap = myMapGraph.begin(); itmap != myMapGraph.end(); ++itmap){

            dessi = itmap->second;

            ouf << "node" << myMapNode[dessi.referent] << " -> node" << myMapNode[dessi.cible] << " [label=\"" << dessi.compteur << "\"];" << endl;

        }

        ouf << "}";
        ouf.close();

    }

}
