package syntax.ast;

/** Impression d'une valeur entière. <p> {@link #expr} */
public class StmtPrint extends Stmt {
  public final Expr expr;

  public StmtPrint(Expr expr) {
    super(expr);
    this.expr = expr;
  }

  public void accept(ASTVisitor v) { v.visit(this); }
}
