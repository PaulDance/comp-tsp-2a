package syntax.ast;

/**
 * Instanciation d'objet.
 * <p>
 * {@link #klassId}
 */
public class ExprNew extends Expr {
    public final Ident klassId;

    public ExprNew(final Ident klassId) {
        super(klassId);
        this.klassId = klassId;
    }

    @Override
    public void accept(final ASTVisitor v) {
        v.visit(this);
    }
}
