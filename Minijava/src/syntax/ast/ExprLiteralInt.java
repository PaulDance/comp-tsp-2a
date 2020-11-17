package syntax.ast;

/**
 * Constante Entière.
 * <p>
 * {@link #value}
 */
public class ExprLiteralInt extends Expr {
    public final Integer value;

    public ExprLiteralInt(final Integer value) {
        this.value = value;
    }

    /** Constructeur altérnatif */
    public ExprLiteralInt(final String s) {
        this.value = Integer.parseInt(s);
    }

    @Override
    public String toString() {
        return super.toString() + " " + this.value;
    }

    @Override
    public void accept(final ASTVisitor v) {
        v.visit(this);
    }
}
