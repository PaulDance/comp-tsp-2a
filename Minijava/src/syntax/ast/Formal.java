package syntax.ast;

/** Paramètre formel de méthode. <p>{@link #typeId}<br>{@link #varId} */
public class Formal extends ASTNode {
  /** Nom de Type */
  public final Type typeId;
  /** Nom de Variable */
  public final Ident varId;

  public Formal(Type typeId, Ident varId) {
    super(typeId, varId);
    this.typeId = typeId;
    this.varId = varId;
  }

  public void accept(ASTVisitor v) { v.visit(this); }
}
