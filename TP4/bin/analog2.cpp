#include <string>
#include "CollectionDeLog.h"
#include <iostream>
using namespace std;

int main(int nbarg, char* com[])
{

    if(nbarg <=1){
        cerr << "input some arguments" << endl;
        return 0;
    } //quand aucun argument n'est entre

    string commandes = "";
    for (int i = 1; i < nbarg; i++) {
        commandes = commandes + " "+ com[i];
    } // mets toutes les commandes en un seul string + on ne prend pas le nom du 1er arg qui est l'executable

    size_t begin=0;
    begin=commandes.find(" ",begin);
    while(begin!=std::string::npos)
    {
        commandes.replace(begin,1,"");
        begin=commandes.find(" ",begin);
    } //enleve tous les espaces (?)

    if(commandes.at(0)=='[')
    {
        int heure=-1;
        string nomdeficher=commandes.substr(commandes.find("]")+1); //nom du fichier log

        int num=commandes.find("]")-commandes.find("[");
        string option=commandes.substr(1,num-1); // a changer car on ne met pas de [] pour les options

        //pour le nom du graphe
        string nomdegraphe;
        if(option.find("-g")!=std::string::npos)
        {
            if(option.find(".dot")==std::string::npos){
                cout<<"the name of the photo must end with'.dot'"<<endl;
            }
            int position =option.find(".dot");
            nomdegraphe=option.substr(option.find("-g"),position+4);
        }

        //pour l'heure
        if(option.find("-t")!=std::string::npos)
        {
            string heure11=option.substr(option.find("-t")+1,2);
            char& heure1 = heure11.at(0);
            char& heure2 = heure11.at(1);
            int time1=heure1-'0';
            if(time1>2||time1<0)
            {
                std::cout<<"invalide time"<<endl;
                return 0;
            }
            int time2=heure2-'0';
            if(time1>9||time1<0)
            {
                std::cout<<"invalide time"<<endl;
                return 0;
            }
            heure=time1*10+time2;
        }
        if(option.find("-g")!=std::string::npos && option.find("-t")==std::string::npos && option.find("-e")==std::string::npos)
        {
            if(option.length()==(nomdegraphe.length()+2))
            {
                CollectionDeLog collection(nomdeficher,'n',-1, 'g', nomdegraphe);
            }
            else
            {
                cout<<"wrong options1"<<endl;
            }

            return 0;
        }
        if(option.find("-g")!=std::string::npos && option.find("-t")!=std::string::npos && option.find("-e")!=std::string::npos)
        {
            if(option.length()==(nomdegraphe.length()+8))
            {
                CollectionDeLog collection(nomdeficher, 'e', heure,'g', nomdegraphe);
            }
            else
            {
                cout<<"wrong options2"<<endl;
            }


            return 0;
        }
        if(option.find("-g")!=std::string::npos && option.find("-t")==std::string::npos && option.find("-e")!=std::string::npos)
        {

            if(option.length()==(nomdegraphe.length()+4))
            {
                CollectionDeLog collection(nomdeficher,'e',-1, 'g', nomdegraphe);
            }
            else
            {
                cout<<"wrong options3"<<endl;
            }



            return 0;
        }
        if(option.find("-g")!=std::string::npos&&option.find("-t")!=std::string::npos&&option.find("-e")==std::string::npos)
        {
            if(option.length()==(nomdegraphe.length()+6))
            {
                CollectionDeLog collection(nomdeficher, 'n',heure, 'g', nomdegraphe);
            }
            else
            {
                cout<<"wrong options4"<<endl;
            }



            return 0;
        }
        if(option.find("-g")==std::string::npos&&option.find("-t")!=std::string::npos&&option.find("-e")!=std::string::npos)
        {
            if(option.length()==6)
            {
                CollectionDeLog collection(nomdeficher, 'e',heure);
            }
            else
            {
                cout<<"wrong options5"<<endl;
            }



            return 0;
        }
        if(option.find("-g")==std::string::npos&&option.find("-t")==std::string::npos&&option.find("-e")!=std::string::npos)
        {
            if(option.length()==2)
            {
                CollectionDeLog collection(nomdeficher, 'e');
            }
            else
            {
                cout<<"wrong options6"<<endl;
            }


            return 0;
        }
        if(option.find("-g")==std::string::npos&&option.find("-t")!=std::string::npos&&option.find("-e")==std::string::npos)
        {
            if(option.length()==4)
            {
                CollectionDeLog collection(nomdeficher,'n',heure);
            }
            else
            {
                cout<<"wrong options7"<<endl;
            }


            return 0;
        }
        if(option.find("-g")==std::string::npos&&option.find("-t")==std::string::npos&&option.find("-e")==std::string::npos)
        {

            if(option.length()==0)
            {
                CollectionDeLog collection(nomdeficher);
            }
            else
            {
                cout<<"wrong options8"<<endl;
            }



            return 0;
        }


    }

    else
    {
        if(commandes.find(".log")!=std::string::npos)
        {
            string nomdeficher=commandes.substr(0); //marche que pour ./run
            cout<<"nomdeficher "<<nomdeficher<<endl;
            CollectionDeLog collection(nomdeficher);
        }
        else
        {
            cout<<"your document must end with .log"<<endl;
        }
    }


    return 0;
}


