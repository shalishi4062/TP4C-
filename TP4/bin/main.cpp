/*************************************************************************
                           CollectionDeLog  -  description
                             -------------------
    début                : 10 janv. 2017
    copyright            : (C) 2017 par jcharlesni
*************************************************************************/

//---------- Réalisation du module <CollectionDeLog> (fichier CollectionDeLog.cpp) -----
// tp2.cpp : Defines the entry point for the console application.
//


#include <string>
#include "CollectionDeLog.h"
#include <iostream>
using namespace std;

int main(void)
{
	
	
	string commande;
	cin>>commande;
	//cout<<commande<<endl;
	//cout<<"here"<<endl;
	const char * debut="$./analog ";
	int begin=0;
			begin=commande.find(" ",begin);
			while(begin!=-1)
			{
			commande.replace(begin,1,"");
			begin=commande.find(" ",begin);
			}
			//cout<<"commande"<<commande<<endl;
	
		if(commande.at(9)=='[')
		{
			string nomdeficher=commande.substr(commande.find("]")+1);
			//cout<<"nomdeficher"<<nomdeficher<<endl; 
			//system("pause");
			int num=commande.find("]")-commande.find("[");
			string option=commande.substr(10,num-1);
			//cout<<"option"<<option<<endl;
			//system("pause");
			/*string suboption1=option.substr(0,2);
            cout<<"suboption1"<<suboption1<<endl;
			system("pause");
			*/
			if(option.find("-g")!=-1)
			{
				int position =option.find(".dot");
				string nomdegraphe=option.substr(option.find("-g"),position+4); 
				//cout<<"nomdegraphe"<<nomdegraphe<<endl;
			}

			if(option.find("-t")!=-1)
			{
				//system("pause");
			string heure11=option.substr(option.find("-t")+1,2);
				cout<<heure11<<endl;
				char& heure1 = heure11.at(0);
				char& heure2 = heure11.at(1);
				int time1=heure1-'0';
				if(time1>2||time1<0)

				{
					std::cout<<"invalide time"<<endl;
					//system ("pause");
					return 0;
				}
				int time2=heure2-'0';
				if(time1>9||time1<0)
				{
					std::cout<<"invalide time"<<endl;
					//system ("pause");
					return 0;
				}
				int heure=time1*10+time2;
			}
			if(option.find("-g")!=-1&&option.find("-t")==-1&&option.find("-e")==-1)
				{
				    CollectionDeLog(nomdeficher,'n',-1, 'g', nomdegraphe);
					//system ("pause");
					return 0;
				}
			    if(option.find("-g")!=-1&&option.find("-t")!=-1&&option.find("-e")!=-1)
				{
					CollectionDeLog(nomdeficher, 'e', heure,'g', nomdegraphe);
					//system ("pause");
					return 0;
				}
				 if(option.find("-g")!=-1&&!option.find("-t")==-1&&option.find("-e")!=-1)
				 {

				    CollectionDeLog(nomdeficher,'e',-1, 'g', nomdegraphe);
					//system ("pause");
					return 0;
				 }
				  if(option.find("-g")!=-1&&option.find("-t")!=-1&&option.find("-e")==-1)
				 {
				    CollectionDeLog(nomdeficher, 'n',heure, 'g', nomdegraphe);
					//system ("pause");
					return 0;
				 }
				  if(option.find("-g")==-1&&option.find("-t")!=-1&&option.find("-e")!=-1)
				 {
				    CollectionDeLog(nomdeficher, 'e',heure);
					//system ("pause");
					return 0;
				 }
				  if(option.find("-g")==-1&&option.find("-t")==-1&&option.find("-e")!=-1)
				 {
				    CollectionDeLog(nomdeficher, 'e');
					//system ("pause");
					return 0;
				 }
				   if(option.find("-g")==-1&&option.find("-t")!=-1&&option.find("-e")==-1)
				 {
				    CollectionDeLog(nomdeficher,'n',heure);
					//system ("pause");
					return 0;
				 }
				    if(option.find("-g")==-1&&option.find("-t")==-1&&option.find("-e")==-1)
				 {
				    CollectionDeLog(nomdeficher);
					//system ("pause");
					return 0;
				 }


		}
			
		else
		{
			string nomdeficher=commande.substr(9);
			cout<<"nomdeficher"<<nomdeficher<<endl;
			CollectionDeLog(nomdeficher);
			return 0;

		}

	
	/*else{

				cout<<"wrong commande"<<endl;
		system ("pause");
		return 0;
	}
	*/
	//system ("pause");
	return 0;
}

