package syntax.ast;

/** Instruction : classse abstraite pour Stmt*. */
public abstract class Stmt extends ASTNode {
    Stmt(final ASTNode... fils) {
        super(fils);
    }
}
