package syntax.ast;

/**
 * Constante Booléenne.
 * <p>
 * {@link #value}
 */
public class ExprLiteralBool extends Expr {
    public final Boolean value;

    public ExprLiteralBool(final Boolean value) {
        this.value = value;
    }

    /** Constructeur altérnatif */
    public ExprLiteralBool(final String s) {
        this.value = Boolean.parseBoolean(s);
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
