package syntax.ast;

/** Instanciation d'un tableau. <p>{@link #size} */
public class ExprArrayNew extends Expr {
  public final Expr size;

  public ExprArrayNew(Expr size) {
    super(size);
    this.size = size;
  }

  public void accept(ASTVisitor v) { v.visit(this); }
}
