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
			cout << "The name of source document must be end with \".log\"." << endl;
			return 0;
		}
		FILE *file = NULL;
		file = fopen(nomdefichier.c_str(), "r");

		if (file == NULL) {
			cout << "The file does not exist or you do not have the reading rights on it." << endl;;
			return 0;
		} // si le fichier n'existe pas
		fclose(file);

		CollectionDeLog collection(nomdefichier, 'n');
	}//S'il n'y a pas d' options, appeler le constructeur sans options

	if (argc <= 1) {
		cout << "input some argument" << endl;
		return 0;
	}//Si aucun argument n'est entre

	if (argc > 2) {
		nomdefichier = argv[argc - 1];

		if (nomdefichier.find(".log") == nomdefichier.npos) {
			cout << "The name of source document must be end with \".log\"." << endl;
			return 0;
		}//Si nomdefichier est pas correct,output erreur.
		FILE *file = NULL;
		file = fopen(nomdefichier.c_str(), "r");

		if (file == NULL) {
			cout << "The file does not exist or you do not have the reading rights on it." << endl;
			return 0;
		} // si le fichier n'existe pas
		fclose(file);

		char option = ' ';
		for (int i = 1; i < (argc - 1); i++) {
			if (argv[i][0] != '-') {
				cout << "argument should start with '-'" << endl;
				return 0;
			} //s'il manque le -
			if (argv[i][2] != '\0') {
				cout << "argument should be only one character" << endl;
				return 0;
			} // si l'argument a plus d'une lettre
			option = argv[i][1]; // option prend la valeur de l'option
			switch (option) {
			case ('t'): {

				string heures = argv[i + 1];
				if(!isdigit(heures[0])){
					cout << "You must precise the hour with command -t." <<endl;
					return 0;
				}
				heure = stoi(heures);
				if (heure >= 24 || heure < 0) {
					cout << "Invalid time ! " << endl;
					return 0;
				}
				i++;//i plus 1 ,pour obtenir la prochaine option
				tflag = 1;//mettre tflag a 1;
				break;
			}
			case ('e'): {
				eflag = 1;//mettre eflag a 1;
				break;
			}
			case ('g'): {
				nomdegraphe = argv[i + 1];
				if (nomdegraphe.find(".dot") == nomdegraphe.npos) {
					cout << "Name of the GraphViz file must be end with\".dot\"." << endl;
					return 0;
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
								cout << "Name of the GraphViz file must be end with\".dot\"." << endl;
								return 0;
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
				cout << "Wrong command" << endl; //a part les symboles corrects,les autres sont tous fauts
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
