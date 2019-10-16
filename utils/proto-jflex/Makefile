# Makefile pour utilisation Jflex Alonr
JAVA = java
JAVAC = javac -g
JFLEX = $(JAVA) -jar jflex.jar -nobak

usage : 
	@echo -e "USAGE : \n\
  make XX :  XX.jflex -> Yylex.java , Yylex.class \n\
  make run, make compile, make clean."

.SUFFIXES: .java .class
.java.class:
	$(JAVAC) $*.java

% :: %.jflex
	@echo "Analyseur lexical : $*.flex ==> Yylex.java, Yylex.class"
	$(JFLEX) $*.jflex
	$(JAVAC) Yylex.java
	@echo "run with : java Yylex "

compile : Yylex.class

run  :  Yylex.class
	$(JAVA) Yylex

clean :
	-/bin/rm -f *~ Yylex.java *.class
