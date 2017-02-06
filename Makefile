

# si on tape "make" sans préciser de cible, make va chercher à
# construire la *première* cible du Makefile.
default: all

all: rebours parexec

##########################################
# compilation des programmes

rebours: rebours.c
	gcc -Wall -Werror -g -o rebours   rebours.c 

parexec: parexec.c
	gcc -Wall -Werror -g -o parexec   parexec.c

##########################################
# nettoyage des fichiers générés

clean:
	rm -f *.o parexec rebours
