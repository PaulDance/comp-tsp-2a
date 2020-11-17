package syntax.ast;

/**
 * Instanciation d'un tableau.
 * <p>
 * {@link #size}
 */
public class ExprArrayNew extends Expr {
    public final Expr size;

    public ExprArrayNew(final Expr size) {
        super(size);
        this.size = size;
    }

    @Override
    public void accept(final ASTVisitor v) {
        v.visit(this);
    }
}
