#include <string>
//#include "CollectionDeLog.h"
#include <iostream>
using namespace std;

int main(string commandes)
{
	
	//const char * commande="$./analog [-e -t -g hhkhkhk.dot]nom.log";
	//string commandes("[-e -t 25]nom.log");
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
			string option=commandes.substr(1,num-1);
			//cout<<"option"<<option<<endl;
			//system("pause");
			/*string suboption1=option.substr(0,2);
                          cout<<"suboption1"<<suboption1<<endl;
			system("pause");
			*/
			string nomdegraphe;
			if(option.find("-g")!=-1)
			{
				if(option.find(".dot")==-1){
				cout<<"the name of the photo mustwnd with'.dot'"<<endl;
				}
				int position =option.find(".dot");
				nomdegraphe=option.substr(option.find("-g"),position+4); 
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
					if(option.length()==(nomdegraphe.length()+2))
				    {
						CollectionDeLog collection(nomdeficher,'n',-1, 'g', nomdegraphe);
					}
					else
					{
						cout<<"wrong options1"<<endl;
					}
					//system ("pause");
					return 0;
				}
			    if(option.find("-g")!=-1&&option.find("-t")!=-1&&option.find("-e")!=-1)
				{
					if(option.length()==(nomdegraphe.length()+8))
				    {
						CollectionDeLog collection(nomdeficher, 'e', heure,'g', nomdegraphe);
					}
					else
					{
						cout<<"wrong options2"<<endl;
					}
					
					//system ("pause");
					return 0;
				}
				 if(option.find("-g")!=-1&&option.find("-t")==-1&&option.find("-e")!=-1)
				 {

					 if(option.length()==(nomdegraphe.length()+4))
				    {
						CollectionDeLog collection(nomdeficher,'e',-1, 'g', nomdegraphe);
					}
					else
					{
						cout<<"wrong options3"<<endl;
					}
					
				    
					//system ("pause");
					return 0;
				 }
				  if(option.find("-g")!=-1&&option.find("-t")!=-1&&option.find("-e")==-1)
				 {
					  if(option.length()==(nomdegraphe.length()+6))
				    {
						CollectionDeLog collection(nomdeficher, 'n',heure, 'g', nomdegraphe);
					}
					else
					{
						cout<<"wrong options4"<<endl;
					}
					
				    
					//system ("pause");
					return 0;
				 }
				  if(option.find("-g")==-1&&option.find("-t")!=-1&&option.find("-e")!=-1)
				 {
					   if(option.length()==6)
				    {
						CollectionDeLog collection(nomdeficher, 'e',heure);
					}
					else
					{
						cout<<"wrong options5"<<endl;
					}
					
				    
					//system ("pause");
					return 0;
				 }
				  if(option.find("-g")==-1&&option.find("-t")==-1&&option.find("-e")!=-1)
				 {
					   if(option.length()==2)
				    {
						 CollectionDeLog collection(nomdeficher, 'e');
					}
					else
					{
						cout<<"wrong options6"<<endl;
					}
				   
					//system ("pause");
					return 0;
				 }
				  if(option.find("-g")==-1&&option.find("-t")!=-1&&option.find("-e")==-1)
				 {
					    if(option.length()==4)
				    {
						  CollectionDeLog collection(nomdeficher,'n',heure);
					}
					else
					{
						cout<<"wrong options7"<<endl;
					}
				   
					//system ("pause");
					return 0;
				 }
				    if(option.find("-g")==-1&&option.find("-t")==-1&&option.find("-e")==-1)
				 {

					    if(option.length()==0)
				    {
						  CollectionDeLog collection(nomdeficher);
					}
					else
					{
						cout<<"wrong options8"<<endl;
					}
				   
				    
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
			CollectionDeLog collection(nomdeficher, 'n');
			}
			else
			{
				cout<<"your docutement must end with .log"<<endl;
			}
		}

	//system ("pause");
	return 0;
}

	
