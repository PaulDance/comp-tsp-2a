import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Scanner;
import java_cup.runtime.ScannerBuffer;
import java_cup.runtime.Symbol;
import java_cup.runtime.lr_parser;


/**
 * Analyse Syntaxique :
 * <li>Encapsulation et adaptations des classes "Yylex" et "parser" générées par
 * Jflex et CUP )
 * <li>Méthode {@link #main} pour analyse autonome
 */
public class CupParse {
    /** File name for standard input */
    private static final String STDIN = "stdin";

    /**
     * @return L'axiome de la grammaire. (type défini dans la spécification Cup
     */
    public Object getAxiom() {
        return this.axiom;
    }

    private Object axiom;

    /**
     * Constructeur
     * 
     * @param file       Fichier en entrée; "stdin" ou "-" pour l'entrée
     *                   standard
     * @param debugToken true = dump des tokens lues par le parseur
     * @param debugParse true = trace detaillée d'execution de l'automate LALR
     * @param pw         java.io.PrinterWriter pour impression/debug
     */
    public CupParse(String file, final boolean debugToken, final boolean debugParse,
            final PrintWriter pw) {
        this.axiom = null;
        if (file.equals("-")) {
            file = CupParse.STDIN;
        }
        // redirect file as stdin
        if (file.equals(CupParse.STDIN)) {
            pw.println("Reading standard input : ");
        } else {
            try {
                System.setIn(new FileInputStream(file));
                pw.println("Reading file " + file);
            } catch (final FileNotFoundException e) {
                pw.println("File " + file + " not found");
                return;
            }
        }
        // create lexer and parser
        final ComplexSymbolFactory csf = new ComplexSymbolFactory();
        final Reader reader = new InputStreamReader(System.in);
        final Scanner lexer = new Yylex(reader, csf);
        final ScannerBuffer scanBuf = new ScannerBuffer(lexer);
        final lr_parser parser = new parser(scanBuf, csf);
        // run parser and check return
        Symbol result;
        try {
            result = debugParse ? parser.debug_parse() : parser.parse();
            if (result == null || result.sym != 0) {
                pw.println("Parsing ends with symbol : " + result);
            } else {
                this.axiom = result.value;
                pw.println("Parsing OK, Axiome = " + this.axiom);
            }
        }
        // capture syntax error from cup !!! bof !!!
        catch (final RuntimeException e) {
            throw e;
        } catch (final Exception e) {
            pw.println(e.getMessage());
        }
        // dump tokens
        finally {
            if (debugToken) {
                pw.println("= Token Debug");
                pw.println(scanBuf.getBuffered().toString());
            }
        }
    }

    /**
     * Main() pour analyse syntaxique autonome
     * 
     * @param args [-help] [-debug] [-trace] [ files ]
     */
    public static void main(final String[] args) {
        final PrintWriter pw = new PrintWriter(System.out, true);
        // parse args
        final String USAGE = " CupParse [-help] [-debug] [-trace] [ files ]"
                + "\n\t -degug \t dump tokens," + "\n\t -trace \t trace LR Automaton"
                + "\n\t files  \t stdin by default";
        boolean debug = false;
        boolean trace = false;
        final java.util.List<String> files = new java.util.ArrayList<>();
        for (final String opt: args) {
            switch (opt) {
                case "-help":
                case "-h":
                case "--help":
                    pw.println(USAGE);
                    System.exit(0);
                    break;

                case "-debug":
                    debug = true;
                    break;

                case "-trace":
                    trace = true;
                    break;

                default:
                    files.add(opt);
            }
        }
        if (files.isEmpty()) {
            files.add(CupParse.STDIN);
        }
        // run
        for (final String file: files) {
            new CupParse(file, debug, trace, pw);
        }
    }
}
