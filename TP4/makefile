# Déclaration des variables
CC = g++
CC_FLAGS = -std=c++11 -Wall 
CC_EXFLAGS = # -DMAP
VAL_FLAGS = --leak-check=yes

#Noms de dossier
BINDIR = bin

# Noms de fichiers
LDFLAGS =
EXEC = analog
SOURCES = $(wildcard $(BINDIR)/*.cpp)
OBJECTS = $(SOURCES:$(BINDIR)/.cpp=.o)	

#Cible principale
$(EXEC) : $(OBJECTS)
	@$(CC) -o $@ $^ $(LDFLAGS) $(CC_FLAGS)
	
# Pour les fichiers objets
%.o: %.cpp
	$(CC) -o $@ -c $< $(CC_FLAGS) $(CC_EXFLAGS)
	
#Pour effacer les fichiers exécutables
clean:mrproper
	rm -rf *.o
mrproper:
	rm -rf $(EXEC)
	
#Pour effacer les fichiers .dot
rmgraph:
	find . -name "*.dot" -type f -delete
	
#Pour valgrind
valgrind:
	valgrind $(VAL_FLAGS) ./$(EXEC)
