#include <cstring>
#include <string>
#include "CollectionDeLog.h"
#include <iostream>
using namespace std;

int main(int argc,char*argv[])
{
	int tflag=0;
	int eflag=0;
	int gflag=0;//3 drapeaux par defaut 0. Si il y a des options,on mettra ces valeurs a 1
	int heure;
	string nomdegraphe;
	string nomdefichier;

	if(argc==2){
		nomdefichier=argv[1];	
		if(nomdefichier.find(".log")==std::string::npos){
			cout<<"The name of source document must be end with \".log\"."<<endl;
			return 0;
		}
		FILE *file = NULL;
		file = fopen(nomdefichier.c_str(),  "r");
 
		if (file == NULL) {
		     cout << "le fichier n'existe pas !" << endl;;
		      return 0;
		} // si le fichier n'existe pas
		fclose(file);

		CollectionDeLog collection(nomdefichier, 'n');
	}//S'il n'y a pas de options,appeler le constructeur sans options

	if(argc <=1){
		cout << "input some argument" << endl;
		return 0;	
	}//Si aucun argument n'est entre

	if(argc>2)
	{
		nomdefichier=argv[argc-1];

		if(nomdefichier.find(".log")==std::string::npos)
		{
			cout<<"The name of source document must be end with \".log\"."<<endl;
			return 0;
		}//Si nomdefichier est pas correct,output erreur.
		FILE *file = NULL;
		file = fopen(nomdefichier.c_str(),  "r"); //ouverture en lecture
 
		if (file == NULL){
		      cout << "le fichier n'existe pas !" << endl;
		      return 0;
		} // si le fichier n'existe pas
		fclose(file);

		char option=' ';
		for(int i=1;i<(argc-1);i++)
		{
			
			if (argv[i][0]!='-'){cout<<"argument should start with '-'"<<endl;return 0;} //s'il manque le -
			if (argv[i][2]!='\0'){cout<<"argument should be only one character"<<endl;return 0;} // si l'argument a plus d'une lettre 
			option = argv[i][1]; // option prend la valeur de l'option
			switch(option)
			{
			case('t'):{

				string heures=argv[i+1];
				try{            //quand l'heure a 2 chiffre,on fait ceux ci
					char& heure1=heures.at(0);
					char& heure2=heures.at(1);
					int time1=heure1-'0';
					int time2=heure2-'0';
					heure=time1*10+time2;//calculer l'heure
					if(heure>=24||heure<0){cout<<"Invalid time!"<<endl;return 0;}
				}
				catch(exception e){  //quand l'heure a seulement 1 chiffre,on fait ceux ci
					string heures=argv[i+1];
					char&time=heures.at(0);
					heure=time-'0';
					if(heure>9||heure<0)
					{cout<<"Invalid times!"<<endl;return 0;}
				}
				i++;//i plus 1 ,pour obtenir la prochaine option
				tflag=1;//mettre tflag a 1;
				break;
					  }
			case('e'):{
				eflag=1;//mettre eflag a 1;
				break;
					  }
			case('g'):{		
				nomdegraphe=argv[i+1];
				if(nomdegraphe.find(".dot")==std::string::npos){
					cout<<"Name of the GraphViz file must be end with\".dot\"."<<endl;
					return 0;
				}//garder le nom de graphe ,output erreur quand il na pas de forme correcte
				gflag=1;//mettre gflag a 1;
				i++;
				break;
					  }
			default:{
				cout<<"Wrong command"<<endl;
				return 0;//a part les symboles corrects,les autres sont tous fauts
					}


			}
		}
	}

	if(gflag==1&&eflag==0&&tflag==0)
	{
		CollectionDeLog collection(nomdefichier,'n',-1, 'g', nomdegraphe);
		return 0;
	}
	if(gflag==1&&eflag==1&&tflag==1)
	{
		CollectionDeLog collection(nomdefichier, 'e', heure,'g', nomdegraphe);
		return 0;
	}

	if(gflag==1&&eflag==1&&tflag==0)
	{
		CollectionDeLog collection(nomdefichier,'e',-1, 'g', nomdegraphe);
		return 0;
	}

	if(gflag==1&&eflag==0&&tflag==1)
	{
		CollectionDeLog collection(nomdefichier, 'n',heure, 'g', nomdegraphe);
		return 0;
	}

	if(gflag==0&&eflag==1&&tflag==1)
	{
		CollectionDeLog collection(nomdefichier, 'e',heure);
		return 0;
	}

	if(gflag==0&&eflag==1&&tflag==0)
	{
		CollectionDeLog collection(nomdefichier, 'e');
		return 0;
	}

	if(gflag==0&&eflag==0&&tflag==1)
	{

		CollectionDeLog collection(nomdefichier,'n',heure);
		return 0;
	}
	return 0;
}
