package syntax.ast;

/**
 * Identificateur de Variable.
 * <p>
 * {@link #varId}
 */
public class ExprIdent extends Expr {
    public final Ident varId;

    public ExprIdent(final Ident varId) {
        super(varId);
        this.varId = varId;
    }

    @Override
    public void accept(final ASTVisitor v) {
        v.visit(this);
    }
}
