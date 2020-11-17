package syntax.ast;

/**
 * Taille d'un tableau.
 * <p>
 * {@link #array}
 */
public class ExprArrayLength extends Expr {
    public final Expr array;

    public ExprArrayLength(final Expr array) {
        super(array);
        this.array = array;
    }

    @Override
    public void accept(final ASTVisitor v) {
        v.visit(this);
    }
}
