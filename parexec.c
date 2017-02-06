#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#include <assert.h>

int main(int argc, char** argv)
{
    pid_t pid;
    int nbEnfant =0;
    int nbMax = atoi(argv[2]);
    const char* path = argv[1];
    int i;
    for(i=argc-1; i>2 ; i--){
		if(nbEnfant>=nbMax){
			wait(NULL);
			nbEnfant--;
		}
		pid = fork();
		if(pid == 0){ //on est dans l'enfant
			execlp(path, path, argv[i], NULL);
		}
		nbEnfant++;
	}
	for(i=0; i<argc-3 ; i++){
		wait(NULL);
	}
	
    return 0;
}
