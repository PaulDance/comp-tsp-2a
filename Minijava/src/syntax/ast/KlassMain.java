package syntax.ast;

/** Déclaration de la classe main(). <p>{@link #klassId}<br>{@link #argId}<br>{@link #stmt} */
public class KlassMain extends ASTNode {
  /** Nom de classe */
  public final Ident klassId;
  /** Argument String[] de main() */
  public final Ident argId;
  /** Instruction unique de main() */
  public final Stmt stmt;

  public KlassMain(Ident klassId, Ident argId, Stmt stmt) {
    super(klassId, argId, stmt);
    this.klassId = klassId;
    this.argId = argId;
    this.stmt = stmt;
  }

  public void accept(ASTVisitor v) { v.visit(this); }
}
