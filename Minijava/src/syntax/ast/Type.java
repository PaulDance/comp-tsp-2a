package syntax.ast;

/** Identificateur de type. <p>{@link #name} */
public class Type extends ASTNode {
  public final String name;

  public Type(String name) { // types references
    this.name = name;
  }

  /** Constructeur altérnatif */
  public Type(main.TYPE t) {  // type primitifs
    this.name = t.toString();
  } 

  @Override
  public String toString() {
    return super.toString() + " " + name;
  }

  public void accept(ASTVisitor v) { v.visit(this); }
}
