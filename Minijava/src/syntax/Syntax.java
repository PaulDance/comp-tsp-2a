package syntax;

import main.CompilerException;
import main.DEBUG;
import syntax.ast.ASTNode;
import syntax.ast.Axiom;


/** Analyse Syntaxique : encapsulation de CupJflex */
public class Syntax {
    /** Structure de donnée en sortue de l'analyse synraxique */
    public ASTNode getResult() {
        return this.axiom;
    }

    private final ASTNode axiom;

    /** @param file Fichier en entrée, "stdin" pour entrée standard */
    public Syntax(final String file) {
        // Analyse jflex-cup
        final Object ast = new CupParse(file, DEBUG.TOKEN, DEBUG.PARSE, DEBUG.PW).getAxiom();

        // AST Valide ?
        if (!(ast instanceof ASTNode)) {
            throw new CompilerException("axiom is not an ASTNode");
        }

        this.axiom = (ASTNode) ast;

        // print AST
        if (DEBUG.TREE) {
            DEBUG.log("= AST");
            DEBUG.log(this.axiom.toPrint());
        }

        // AST Visitable ?
        if (!(this.axiom instanceof Axiom)) {
            throw new CompilerException("axiom is not an Axiom");
        }

        // Premiere visite
        if (DEBUG.PRETTY) {
            DEBUG.log("= Pretty Print");
            DEBUG.log(new syntax.PrettyPrint(this.axiom));
        }
    }
}
