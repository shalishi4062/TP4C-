#include"stdafx.h"
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
	string nomdeficher;

	if(argc==2){
		nomdeficher=argv[1];	
		if(nomdeficher.find(".log")==std::string::npos){
			cout<<"The name of source document must be end with \".log\"."<<endl;
			return 0;
		}
		CollectionDeLog collection(nomdeficher, 'n');
	}//Si il n 'y a pas de options,appeler le constructeur sans options


	if(argc>2)
	{
		string nomdeficher=argv[argc-1];
		if(nomdeficher.find(".log")==std::string::npos)
		{
			cout<<"The name of source document must be end with \".log\"."<<endl;
			return 0;
		}//Si nomdeficher est pas correct,output erreur.

		char option=' ';
		for(int i=1;i<(argc-1);i++)
		{
			if (strcmp(argv[i],"-t")==0){option='t';}
			if (strcmp(argv[i],"-e")==0){option='e';}
			if (strcmp(argv[i],"-g")==0){option='g';}
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
					if(heure>24||heure<0){cout<<"Invalid time!"<<endl;return 0;}
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
					cout<<"Name of the photo must be end with\".dot\"."<<endl;
					return 0;
				}//grader le nom de graphe ,output erreur quand il na pas de forme correcte
				gflag=1;//mettre gflag a 1;
				i++;
				break;
					  }
			default:{
				cout<<"Wrong command"<<endl;
				return 0;//a part de tous les sympols corrects,les autre tous sont fautes
					}


			}
		}
	}

	if(gflag==1&&eflag==0&&tflag==0)
	{
		CollectionDeLog collection(nomdeficher,'n',-1, 'g', nomdegraphe);
		return 0;
	}
	if(gflag==1&&eflag==1&&tflag==1)
	{
		CollectionDeLog collection(nomdeficher, 'e', heure,'g', nomdegraphe);
		return 0;
	}

	if(gflag==1&&eflag==1&&tflag==0)
	{
		CollectionDeLog collection(nomdeficher,'e',-1, 'g', nomdegraphe);
		return 0;
	}

	if(gflag==1&&eflag==0&&tflag==1)
	{
		CollectionDeLog collection(nomdeficher, 'n',heure, 'g', nomdegraphe);
		return 0;
	}

	if(gflag==0&&eflag==1&&tflag==1)
	{
		CollectionDeLog collection(nomdeficher, 'e',heure);
		return 0;
	}

	if(gflag==0&&eflag==1&&tflag==0)
	{
		CollectionDeLog collection(nomdeficher, 'e');
		return 0;
	}

	if(gflag==0&&eflag==0&&tflag==1)
	{

		CollectionDeLog collection(nomdeficher,'n',heure);
		return 0;
	}
	return 0;
}