#include <cstring>
#include <string>
#include "CollectionDeLog.h"
using namespace std;

int main(int argc,char*argv[]) {
	int tflag = 0;
	int eflag = 0;
	int gflag = 0;//3 drapeaux par defaut 0. Si il y a des options,on mettra ces valeurs a 1
	int heure;
	string nomdegraphe;
	string nomdefichier;

	if (argc == 2) {
		nomdefichier = argv[1];
		if (nomdefichier.find(".log") == nomdefichier.npos) {
			cerr << "Le nom du fichier doit se finir par \".log\"." << endl;
			return 2;
		}
		FILE *file = NULL;
		file = fopen(nomdefichier.c_str(), "r");

		if (file == NULL) {
			cerr << "Le fichier n'existe pas ou vous n'avez pas les droits en lecture dessus." << endl;;
			return 3;
		} // si le fichier n'existe pas
		fclose(file);

		CollectionDeLog collection(nomdefichier, 'n');
	}//S'il n'y a pas d' options, appeler le constructeur sans options

	if (argc <= 1) {
		cerr << "Entrez des arguments" << endl;
		return 1;
	}//Si aucun argument n'est entre

	if (argc > 2) {
		nomdefichier = argv[argc - 1];

		if (nomdefichier.find(".log") == nomdefichier.npos) {
			cerr << "Le nom du fichier doit se finir par \".log\"." << endl;
			return 2;
		}//Si nomdefichier est pas correct,output erreur.
		FILE *file = NULL;
		file = fopen(nomdefichier.c_str(), "r");

		if (file == NULL) {
			cerr << "Le fichier n'existe pas ou vous n'avez pas les droits en lecture dessus." << endl;
			return 3;
		} // si le fichier n'existe pas
		fclose(file);

		char option = ' ';
		for (int i = 1; i < (argc - 1); i++) {
			if (argv[i][0] != '-') {
				cerr << "Un argument doit commencer par '-'." << endl;
				return 4;
			} //s'il manque le -
			if (argv[i][2] != '\0') {
				cerr << "Un argument ne doit faire que 1 caractere." << endl;
				return 4;
			} // si l'argument a plus d'une lettre
			option = argv[i][1]; // option prend la valeur de l'option
			switch (option) {
			case ('t'): {
                                if(tflag==1) 
				{
					cerr << "Ecriture double de l'argument -t !" << endl;
					return 6;
				}
				string heures = argv[i + 1];
				if(!isdigit(heures[0])){
					cerr << "Precisez l'heure en chiffre apres l'argument -t." <<endl;
					return 5;
				}
				heure = stoi(heures);
				if (heure >= 24 || heure < 0) {
					cerr << "Heure invalide ! " << endl;
					return 7;
				}
				i++;//i plus 1 ,pour obtenir la prochaine option
				
				tflag = 1;//mettre tflag a 1;
				break;
			}
			case ('e'): {
				  if(eflag==1) 
				{
					cerr << "Ecriture double de l'argument -e !" << endl;
					return 6;
				}
				eflag = 1;//mettre eflag a 1;
				break;
			}
			case ('g'): {
				  if(gflag==1) 
				{
					cerr << "Ecriture double de l'argument -g !" << endl;
					return 6;
				}
				nomdegraphe = argv[i + 1];
				if (nomdegraphe.find(".dot") == nomdegraphe.npos) {
					cerr << "Le nom du fichier GraphViz doit finir par \".dot\"." << endl;
					return 5;
				}//erreur quand il n a pas de forme correcte

				//Test existante fichier graph
				FILE *file = NULL;
				char rep;
				bool bonNomFichier = false;
				while (!bonNomFichier){
					file = fopen(nomdegraphe.c_str(), "r");
					if (file != NULL) {
						fclose(file);
						cout << "Le fichier existe deja" << endl << "Entrez 1 pour ecrire un nouveau nom ou Entrez 2 pour effacer l'ancien fichier" << endl;
						cin >> rep;
						while(rep != '1' && rep != '2'){
							cout << "Entrez 1 pour ecrire un nouveau nom ou Entrez 2 pour effacer l'ancien fichier" << endl;
							cin >> rep;
						}
						if (rep =='1'){
							cout << "Entrez le nouveau nom" << endl;
							cin >> nomdegraphe;
							if (nomdegraphe.find(".dot") == nomdegraphe.npos) {
								cerr << "Le nom du fichier GraphViz doit finir par \".dot\"." << endl;
								return 5;
							}//erreur quand il n a pas de forme correcte

						}else{
							bonNomFichier=true;
							cout << "Fichier " << nomdegraphe << " efface." << endl;
						}
					} // si le fichier graph existe deja
					else {bonNomFichier = true;}
				}
				gflag = 1;//mettre gflag a 1;
				i++;
				break;
			}
			default: {
				cerr << "Mauvais argument" << endl; //a part les symboles corrects,les autres sont tous fauts
			return 7;				
			}


			}
		}
	}

	if (gflag == 1) {
		if (eflag == 1) {
			if (tflag == 1) {
				CollectionDeLog collection(nomdefichier, 'e', heure, 'g', nomdegraphe);
			} else {
				CollectionDeLog collection(nomdefichier, 'e', -1, 'g', nomdegraphe);
			}
		} else {
			if (tflag == 1) {
				CollectionDeLog collection(nomdefichier, 'n', heure, 'g', nomdegraphe);
			} else {
				CollectionDeLog collection(nomdefichier, 'n', -1, 'g', nomdegraphe);
			}
		}
	} else if (eflag == 1) {
		if (tflag == 1) {
			CollectionDeLog collection(nomdefichier, 'e', heure);
		} else {
			CollectionDeLog collection(nomdefichier, 'e');
		}
	} else if (tflag == 1) {
		CollectionDeLog collection(nomdefichier, 'n', heure);
	}

	return 0;
}
