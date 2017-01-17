// tp2.cpp : Defines the entry point for the console application.
//


#include <string>
#include "CollectionDeLog.h"
#include <iostream>
using namespace std;

int main(string commandes)
{
	
	//const char * commande="$./analog [-e -t 45]nom.log";
	//string commandes("[-e -t 45]nom.log");
	//cout<<commandes<<endl;
	//cout<<"here"<<endl;
	
	int begin=0;
			begin=commandes.find(" ",begin);
			while(begin!=-1)
			{
			commandes.replace(begin,1,"");
			begin=commandes.find(" ",begin);
			}
			//cout<<"commandes"<<commandes<<endl;
		if(commandes.at(0)=='[')
		{
			string nomdeficher=commandes.substr(commandes.find("]")+1);
			//cout<<"nomdeficher"<<nomdeficher<<endl; 
			//system("pause");
			int num=commandes.find("]")-commandes.find("[");
			string option=commandes.substr(10,num-1);
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
					system ("pause");
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
			if(commandes.find(".log")!=-1)
			{
			string nomdeficher=commandes.substr(9);
			cout<<"nomdeficher"<<nomdeficher<<endl;
			//CollectionDeLog(nomdeficher, 'n');
			}
			else
			{
				cout<<"your docutement must end with .log"<<endl;
			}
		}

	//system ("pause");
	return 0;
}

	
