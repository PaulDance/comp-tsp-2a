package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import codegen.CodeGen;
import intermediate.IR;
import intermediate.Intermediate;
import semantic.Semantic;
import semantic.SemanticTree;
import syntax.Syntax;
import syntax.ast.ASTNode;


/** Main du Compilateur Minijava **/
public class Compiler {
	public static void main(String[] args) {
		if (args.length == 0) {
			args = DEBUG.ARGS;
		}
		
		for (String file: args) {
			new Compiler(file);
		}
	}
	
	Compiler(String infile) {
		try {
			DEBUG.log("=== Analyse Lexicale et Syntaxique ===");
			ASTNode axiom = new Syntax(infile).getResult();
			
			DEBUG.log("=== Analyse Sémantique ===");
			SemanticTree st = new Semantic(axiom).getResult();
			
			DEBUG.log("=== Génération Représentation Intermédiaire ===");
			IR ir = new Intermediate(st).getResult();
			
			DEBUG.log("=== Génération Code ===");
			String outfile = new CodeGen(ir, infile).getResult();
			
			if (DEBUG.RUNMARS) { // may be not here
				DEBUG.log("=== Exécution Mars de " + outfile + " ===");
				this.execCmd("java", "-jar", "lib/mars.jar", "nc", outfile);
			}
		}
		catch (CompilerException | IOException e) {
			DEBUG.logErr("Compilation aborted: " + e.getMessage());
		}
	}
	
	/** Exécution d'une commande dans un processus externe. */
	private void execCmd(String... cmd) throws IOException {
		BufferedReader std;
		String s;
		Process p = Runtime.getRuntime().exec(cmd);
		std = new BufferedReader(new InputStreamReader(p.getInputStream()));
		
		while ((s = std.readLine()) != null) {
			DEBUG.log(s);
		}
		
		std = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		
		while ((s = std.readLine()) != null) {
			DEBUG.logErr(s);
		}
	}
}
