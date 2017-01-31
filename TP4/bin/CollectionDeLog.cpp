/*************************************************************************
                           CollectionDeLog  -  classe CollectionDeLog
                             -------------------
    début                : ??/??/2017
    copyright            : (C) 2017 par Julien Charles-Nicolas,
					Shali Shi, 
					Fatima-Ezzahra Mezidi
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


CollectionDeLog::CollectionDeLog ( )
// aucune des structure de donnees n'est initialisee
{
#ifdef MAP
    cout << "Appel au constructeur par defaut de <CollectionDeLog>" << endl;
#endif
} //----- Fin de CollectionDeLog (constructeur par defaut) 

CollectionDeLog::CollectionDeLog (const CollectionDeLog & unCollectionDeLog)
//a supprimer ?? on ne l'utilise pas et sert a rien meme dans la reutilisabilite je pense
{
#ifdef MAP
    cout << "Appel au constructeur de copie de <CollectionDeLog>" << endl;
#endif
} //----- Fin de CollectionDeLog (constructeur de copie)


CollectionDeLog::CollectionDeLog(string nf, char e, int h, char topdix, string nfGraph) 
//initie les attributs: le fichier a lire et les tris, ainsi qu'apelle les methodes pour remplir les structures de donnees
{
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

} //----- Fin de CollectionDeLog (constructeur)


CollectionDeLog::~CollectionDeLog() 
// destructeur
{
#ifdef MAP
    cout << "Appel au destructeur de <CollectionDeLog>" << endl;
#endif
} //Fin de ~CollectionDeLog (destructeur)

void CollectionDeLog::RemplirMapTopDix()
// rempli la structure de donnees avec les donnes du fichier contenant les logs
// remplissage effectue selon les tri
// garde la cible et son nombre de hits
{
#ifdef MAP
    cout << "Appel de RemplirMapTopDix de <CollectionDeLog>" << endl;
#endif
    // message a la sortie standard si tri sur l'heure
    if(heure!=-1) cout << "Warning : only hits between " << heure << "h and "<< heure+1 << "h have been taken into account" << endl;
   
    Log l;
    
    // ouverture du fichier contenant les logs
    ifstream file ( nomFichier.c_str() );
    if(file.good()){

        while(file >> l){
            //pour ne garder que la partie importante de la cible
            l.splitRefCib();

	    // tri
            int s = 0; // code de la requete
            if(isdigit(l.Infos.status[0])) s= stoi(l.Infos.status);
            if(s==200) { //requete succes
                if (((exclusion && exclus.find(l.GetType()) == exclus.end()) &&
                     (heure != -1 && l.GetHeure() == heure)) || (!exclusion && heure == -1) ||
                    (!exclusion && heure != -1 && l.GetHeure() == heure) || (heure == -1 && (exclusion &&
                                                                                             exclus.find(
                                                                                                     l.GetType()) ==
                                                                                             exclus.end()))) {
                    //verification de l'existence de la cible dans la structure de donnees
                    if (myMapTopDix.size() != 0 && myMapTopDix.find(l.cible) != myMapTopDix.end()) {
                        myMapTopDix[l.cible] += 1; // ajoute un hit
                    } else {
                        if (!l.cible.empty()) {
                            myMapTopDix.insert(make_pair(l.cible, 1)); // ajoute la nouvelle cible
                        }
                    }
                }
            }
        }
    }

} //-----Fin de RemplirMapTopDix

void CollectionDeLog::RemplirMapGraph() 
// rempli la structure de donnees avec les donnes du fichier contenant les logs
// remplissage effectue selon les tri
// garde la cible ainsi que la reference et leur nombre de
{
#ifdef MAP
    cout << "Appel de RemplirMapGraph de <CollectionDeLog>" << endl;
#endif

    Log l;
    string cibref;
    dessin dessi;

    // ouverture du fichier contenant les logs
    ifstream file ( nomFichier.c_str() );
    if(file.good()){
        while(file >> l){
            //tri
            int s = 0;
            if(isdigit(l.Infos.status[0])) s= stoi(l.Infos.status);
            if(s==200) { // requete succes
                if (((exclusion && exclus.find(l.GetType()) == exclus.end()) &&
                     (heure != -1 && l.GetHeure() == heure)) || (!exclusion && heure == -1) ||
                    (!exclusion && heure != -1 && l.GetHeure() == heure) || (heure == -1 && (exclusion &&
                                                                                             exclus.find(
                                                                                                     l.GetType()) ==
                                                                                             exclus.end()))) {
                    cibref = l.cible + l.Infos.referent; //clé de la map

                    //verification de l'existence de cible-reference
                    if (myMapGraph.size() != 0 && myMapGraph.find(cibref) != myMapGraph.end()) {
                        myMapGraph[cibref].compteur += 1; // ajoute 1 au nombre de fois que referent -> cible
                    } else {

                        if (!l.cible.empty()) {
			    // creation du nouveau cible-reference
                            dessi.compteur = 1; //contient le nombre de fois que referent -> cible a été effectué
                            dessi.cible = "\"" + l.cible +"\"";
                            if(dessi.cible.find("http://")!= dessi.cible.npos) {
                                size_t pos = dessi.cible.find(".fr");
                                dessi.cible = "\""+ dessi.cible.substr(pos+3);
                            }
                            dessi.referent = l.Infos.referent; 
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
}//-----Fin de RemplirMapGraph

void CollectionDeLog::AfficherTopDix()
// Trouve les 10 pages les plus hits et les affiches sur la sortie standard
{
#ifdef MAP
    cout << "Appel de AfficherTopDix de <CollectionDeLog>" << endl;
#endif

    RemplirMapTopDix();

    Log l;

    typedef multimap <int, string> setTopDix; 
    setTopDix mySetTopDix; //contient tous les logs tries selon leur nombre de hits

    mapTopDix::const_iterator itmap;

    for(itmap = myMapTopDix.begin(); itmap != myMapTopDix.end(); ++itmap){
        mySetTopDix.insert(make_pair(itmap->second, itmap->first));
    } // repli le set

    int compt =0;
    setTopDix::const_iterator itset;

    //Affichage sur la sortie standard
    for(itset = mySetTopDix.end() ; compt <11 && itset !=mySetTopDix.begin(); --itset){
        if(itset!=mySetTopDix.end()) {
            cout << itset->second << " (" << itset->first << " hits)" << endl;
        }
        compt++;
    }
    if(compt<11) cout << itset->second << "  (" << itset->first << " hits)" << endl;

}//-----Fin de AfficherTopDix 

void CollectionDeLog::EnregistrerGraph(string nfGraph)
//Enregistre le fichier au format GraphViz pour que le graphe puisse etre dessiner ensuite
{
#ifdef MAP
    cout << "Appel de EnregistrerGraph de <CollectionDeLog>" << endl;
#endif

    cout << "Dot-file " << nfGraph << " generated" << endl;
    AfficherTopDix();
    RemplirMapGraph();

    typedef map <string, int> mapNode; 
    mapNode myMapNode; //elle contiendra le nom d'une page (cible ou referent) et le numero de node associe

    int nombreNode = 0;

    mapTopDix::const_iterator itmap;

    dessin dessi;

    //ouverture du fichier ou on va ecrire
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
} //-----Fin de EnregistrerGraph
