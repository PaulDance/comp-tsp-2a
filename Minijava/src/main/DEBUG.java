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
    public static final boolean TREE = false;
    /** Ajout des "Locations" dans l'impression de l'AST */
    public static final boolean TREELOCATION = true;
    /** PrettyPrint minijava par visite de l'AST */
    public static final boolean PRETTY = false;
    /** Impression de la table des symboles */
    public static final boolean SYMTAB = false;
    /** Impression des Variables non utilisées */
    public static final boolean UNUSED = true;
    /** Impression de la forme intermédiaire */
    public static final boolean INTERMED = false;
    /** Dump de l'allocation Mémoire */
    public static final boolean ALLOCATOR = false;
    /** Execution avec Mars du résultat de la compilation */
    public static final boolean RUNMARS = true;

    /** Flots d'impressions */
    public static final PrintWriter PW = new PrintWriter(System.out, true);
    /** Flots d'impressions en erreur */
    public static final PrintWriter PWERR = new PrintWriter(System.err, true);

    /** remplacement de "System.out.println()" */
    public static void log(final Object o) {
        DEBUG.PW.println(o.toString());
    }

    /** remplacement de "System.err.println()" */
    public static void logErr(final Object o) {
        DEBUG.PWERR.println(o.toString());
    }

    /** fin provisoire de compilation pour cause de travaux */
    public static void toBeContinued() {
        throw new CompilerException("To Be Continued");
    }

}
