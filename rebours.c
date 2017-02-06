#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>
#include <assert.h>

int main(int argc, char** argv)
{
	int n = atoi(argv[1]);
	assert(n>0);
	int pid = getpid();
    printf("%d: debut\n", pid);
    while(n>0){
		printf("%d: %d\n", pid, n);
		n--;
		sleep(1);
	}
	printf("%d: fin\n", pid);
	assert(n==0);
    return 0;
}
