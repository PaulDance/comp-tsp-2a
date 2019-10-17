/** Noeud AST géneérique "non typée" (== CST): String label */
public class AST extends ASTNode {
  public final String label;

  public AST(String label, ASTNode... fils) {
    super(fils);
    this.label = label;
  }

  public void accept(ASTVisitor v) { v.visit(this); }

  @Override
  public String toString() { return label; }
}

