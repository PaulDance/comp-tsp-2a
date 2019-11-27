package main;

import java.io.*;

/** Main du Compilateur Minijava **/
public class Compiler {
  public static void main(String[] args) {
    if (args.length == 0)
      args = DEBUG.ARGS;
    for (String file : args)
      new Compiler(file);
  }

  Compiler(String infile) {
    try {
      DEBUG.log("=== Analyse Lexicale et Syntaxique ===");
      syntax.ast.ASTNode axiom = new syntax.Syntax(infile).getResult();

      // DEBUG.toBeContinued()

      DEBUG.log("=== Analyse Semantique ===");
      semantic.SemanticTree st = new semantic.Semantic(axiom).getResult();

      DEBUG.log("=== Generation Representation Intermediaire ===");
      intermediate.IR ir = new intermediate.Intermediate(st).getResult();

      DEBUG.log("=== Generation Code ===");
      String outfile = new codegen.CodeGen(ir, infile).getResult();

      if (DEBUG.RUNMARS) { // may be not here
        DEBUG.log("== Execution Mars de " + outfile + " ===");
        execCmd("java", "-jar", "lib/mars.jar", "nc", outfile);
      }
    } catch (CompilerException | IOException e) {
      DEBUG.logErr("Compilation aborted : " + e.getMessage());
    }
  }

  /** Execution d'une commande dans un procesus externe */
  private void execCmd(String... cmd) throws IOException {
    BufferedReader std;
    String s;
    Process p = Runtime.getRuntime().exec(cmd);
    std = new BufferedReader(new InputStreamReader(p.getInputStream()));
    while ((s = std.readLine()) != null)
      DEBUG.log(s);
    std = new BufferedReader(new InputStreamReader(p.getErrorStream()));
    while ((s = std.readLine()) != null)
      DEBUG.logErr(s);
  }
}
