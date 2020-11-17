package syntax.ast;

/**
 * Impression d'une valeur entière.
 * <p>
 * {@link #expr}
 */
public class StmtPrint extends Stmt {
    public final Expr expr;

    public StmtPrint(final Expr expr) {
        super(expr);
        this.expr = expr;
    }

    @Override
    public void accept(final ASTVisitor v) {
        v.visit(this);
    }
}
