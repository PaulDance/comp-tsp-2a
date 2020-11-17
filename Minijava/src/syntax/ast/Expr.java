package syntax.ast;

/** Expression : classe abstraite pour Expr*. */
public abstract class Expr extends ASTNode {
    Expr(final ASTNode... fils) {
        super(fils);
    }
}
