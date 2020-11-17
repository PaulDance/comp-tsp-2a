package syntax.ast;

/**
 * Opérateur Binaire.
 * <p>
 * {@link #expr1}<br>
 * {@link #op}<br>
 * {@link #expr2}
 */
public class ExprOpBin extends Expr {
    public final Expr expr1;
    public final main.OPER op;
    public final Expr expr2;

    public ExprOpBin(final Expr expr1, final main.OPER op, final Expr expr2) {
        super(expr1, expr2);
        this.expr1 = expr1;
        this.op = op;
        this.expr2 = expr2;
    }

    @Override
    public String toString() {
        return super.toString() + " " + this.op.name();
    }

    @Override
    public void accept(final ASTVisitor v) {
        v.visit(this);
    }
}
