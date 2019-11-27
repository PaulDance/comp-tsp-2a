package syntax.ast;

/** Identificateur (classe, methode, champs, variable). <p>{@link #name} */
public class Ident extends ASTNode {
  public final String name;

  public Ident(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return super.toString() + " " + name;
  }

  public void accept(ASTVisitor v) { v.visit(this); }
}
