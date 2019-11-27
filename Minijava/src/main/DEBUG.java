package main;

import java.io.PrintWriter;


/** Running Options and helper for main() */
public class DEBUG {
	private DEBUG() {
		throw new IllegalStateException("Utility class");
	}
	
	/** Arguments par defaut du compilateur */
	public static final String[] ARGS = {"input.txt"};
	
	/** Impression des Tokens lus par le parser */
	public static final boolean TOKEN = false;
	/** Trace d'execution de l'automate LR */
	public static final boolean PARSE = false;
	/** Impression de l'AST */
	public static final boolean TREE = true;
	/** Ajout des "Locations" dans l'impression de l'AST */
	public static final boolean TREELOCATION = true;
	/** PrettyPrint minijava par visite de l'AST */
	public static final boolean PRETTY = true;
	/** Impression de la table des symboles */
	public static final boolean SYMTAB = true;
	/** Impression des Variables non utilisées */
	public static final boolean UNUSED = true;
	/** Impression de la forme intermédiaire */
	public static final boolean INTERMED = true;
	/** Dump de l'allocation Mémoire */
	public static final boolean ALLOCATOR = true;
	/** Execution avec Mars du résultat de la compilation */
	public static final boolean RUNMARS = true;
	
	/** Flots d'impressions */
	public static final PrintWriter PW = new PrintWriter(System.out, true);
	/** Flots d'impressions en erreur */
	public static final PrintWriter PWERR = new PrintWriter(System.err, true);
	
	/** remplacement de "System.out.println()" */
	public static void log(Object o) {
		PW.println(o.toString());
	}
	
	/** remplacement de "System.err.println()" */
	public static void logErr(Object o) {
		PWERR.println(o.toString());
	}
	
	/** fin provisoire de compilation pour cause de travaux */
	public static void toBeContinued() {
		throw new CompilerException("To Be Continued");
	}
	
}
