# generate program outputs using default JVM

JAVAFILES= $(wildcard *.java)
OUTFILES=  $(patsubst %.java, %.out, $(JAVAFILES))

goal:	$(OUTFILES)

clean:
	rm -f *.out *.class

%.out:	%.java
	-javac $<
	-java $(basename $<) &> $@
	rm -f *.class
