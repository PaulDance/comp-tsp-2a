package syntax.ast;

/** Expression : classe abstraite pour Expr*. */
public abstract class Expr extends ASTNode {
  Expr(ASTNode... fils) { super(fils); }
}
