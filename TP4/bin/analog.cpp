#include <string>
#include "CollectionDeLog.h"
#include <iostream>
using namespace std;

int main(int argc,char*argv[])
{
	char * s="analog";
	if(strcmp(argv[0],s)!=0){
		//cout<<argv[0]<<endl;
		cout<<"The commande should be start with \"analog\"."<<endl;
		return 0;
	}
	//const char * commande="$./analog [-e -t -g hhkhkhk.dot]nom.log";
	//string commandes("[-e -t 25]nom.log");
	//cout<<commandes<<endl;
	//cout<<"here"<<endl;

	/*         int begin=0;
	begin=commandes.find(" ",begin);
	while(begin!=-1)
	{
	commandes.replace(begin,1,"");
	begin=commandes.find(" ",begin);
	}*/
	//cout<<"commandes"<<commandes<<endl;
	string commandes;
	for(int i=1;i<argc;i++){
		commandes.append(argv[i]);
	}
	if(commandes.find(".log")==-1)
	{
		cout<<"The name of source document must be end with \".log\"."<<endl;
		return 0;
	}
	else{
		if(argc>2){
		cout<<"Wrong command!"<<endl;
		return 0;
		}
	    string nomdeficher=argv[1];
	}
	if(commandes.at(0)=='[')
	{
		string nomdeficher=commandes.substr(commandes.find("]")+1);
		//cout<<"nomdeficher"<<nomdeficher<<endl; 
		//system("pause");
		int num=commandes.find("]")-commandes.find("[");
		string option=commandes.substr(0,num+1);
		//cout<<"option"<<option<<endl;
		//system("pause");
		/*string suboption1=option.substr(0,2);
		cout<<"suboption1"<<suboption1<<endl;
		system("pause");
		*/
		string nomdegraphe;
		int heure;
		if(option.find("-g")!=-1)
		{
			if(option.find(".dot")==-1){
				cout<<"Name of the photo must be end with\".dot\"."<<endl;
				return 0;
			}
			int positionbegin =option.find("-g");
			int positionend=option.find(".dot");
			nomdegraphe=option.substr(positionbegin+2,(positionend-positionbegin+2)); 
		}

		if(option.find("-t")!=-1)
		{

			string heure11=option.substr(option.find("-t")+2,2);
			cout<<heure11<<endl;
			try{char& heure1 = heure11.at(0);
			char& heure2 = heure11.at(1);
			int time1=heure1-'0';
			int time2=heure2-'0';
			if(time1>9||time1<0){cout<<"Invalid time!"<<endl;return 0;}
			if(time2>9||time2<0)
			{
				if(time1>9||time1<0)
				{
					cout<<"Invalide time!"<<endl;
					return 0;
				}
				heure=time1;
			}
			else{
				heure=time1*10+time2;
				if(heure>24||heure<0)
				{
					cout<<"Invalid time!"<<endl;
					return 0;
				}
				cout<<heure<<endl;
			}
			}
			catch(exception e){
				cout<<"Invalid time!"<<endl;return 0;
			}
		}


		if(option.find("-g")!=-1&&option.find("-t")==-1&&option.find("-e")==-1)
		{
			if(option.length()==(nomdegraphe.length()+4))
			{
				CollectionDeLog collection(nomdeficher,'n',-1, 'g', nomdegraphe);
				//cout<<" CollectionDeLog collection(nomdeficher,'n',-1, 'g', nomdegraphe)"<<endl;
			}
			else
			{
				cout<<"Wrong options!"<<endl;
			}
			return 0;
		}
		if(option.find("-g")!=-1&&option.find("-t")!=-1&&option.find("-e")!=-1)
		{
			int sizeh;
			if (heure>=10){sizeh=2;}else{sizeh=1;}
			if(option.length()==(nomdegraphe.length()+8+sizeh))
			{
				CollectionDeLog collection(nomdeficher, 'e', heure,'g', nomdegraphe);
				//cout<<" CollectionDeLog collection(nomdeficher, 'e', heure,'g', nomdegraphe);"<<endl;
			}
			else
			{
				cout<<"Wrong options!"<<endl;
			}
			return 0;
		}

		if(option.find("-g")!=-1&&option.find("-t")==-1&&option.find("-e")!=-1)
		{

			if(option.length()==(nomdegraphe.length()+6))
			{
				CollectionDeLog collection(nomdeficher,'e',-1, 'g', nomdegraphe);
				//cout<<"CollectionDeLog collection(nomdeficher,'e',-1, 'g', nomdegraphe)"<<endl;
			}
			else
			{
				cout<<"Wrong options!"<<endl;
			}
			return 0;
		}

		if(option.find("-g")!=-1&&option.find("-t")!=-1&&option.find("-e")==-1)
		{
			int sizeh;
			if (heure>=10){sizeh=2;}else{sizeh=1;}
			if(option.length()==(nomdegraphe.length()+6+sizeh))
			{
				CollectionDeLog collection(nomdeficher, 'n',heure, 'g', nomdegraphe);
				//cout<<"CollectionDeLog collection(nomdeficher, 'n',heure, 'g', nomdegraphe);"<<endl;
			}
			else
			{
				cout<<"Wrong options!"<<endl;
			}
			return 0;
		}

		if(option.find("-g")==-1&&option.find("-t")!=-1&&option.find("-e")!=-1)
		{
			int sizeh;
			if (heure>=10){sizeh=2;}else{sizeh=1;}
			if(option.length()==(6+sizeh))
			{
				CollectionDeLog collection(nomdeficher, 'e',heure);
				//cout<<"CollectionDeLog collection(nomdeficher, 'e',heure)"<<endl;
			}
			else
			{
				cout<<"Wrong options!"<<endl;
			}
			return 0;
		}

		if(option.find("-g")==-1&&option.find("-t")==-1&&option.find("-e")!=-1)
		{
			if(option.length()==4)
			{
				CollectionDeLog collection(nomdeficher, 'e');
				//cout<<"CollectionDeLog collection(nomdeficher, 'e')"<<endl;
			}
			else
			{
				cout<<"Wrong options!"<<endl;
			}
			return 0;
		}

		if(option.find("-g")==-1&&option.find("-t")!=-1&&option.find("-e")==-1)
		{
			int sizeh;
			if (heure>=10){sizeh=2;}else{sizeh=1;}
			if(option.length()==(4+sizeh))
			{
				CollectionDeLog collection(nomdeficher,'n',heure);
				//cout<<"CollectionDeLog collection(nomdeficher,'n',heure)"<<endl;
			}
			else
			{
				cout<<"Wrong options!"<<endl;
			}
			return 0;
		}

		if(option.find("-g")==-1&&option.find("-t")==-1&&option.find("-e")==-1)
		{

			if(option.length()==2)
			{
				CollectionDeLog collection(nomdeficher);
				//cout<<"CollectionDeLog collection(nomdeficher)"<<endl;
			}
			else
			{
				cout<<"Wrong options!"<<endl;
			}
			return 0;
		}
	}

	else
	{			
		CollectionDeLog collection(nomdeficher, 'n');
		//cout<<"CollectionDeLog collection(nomdeficher, 'n')"<<endl;
	}
	return 0;
}

	
