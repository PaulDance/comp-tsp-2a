package syntax.ast;

/**
 * Appel de méthode d'objet.
 * <p>
 * {@link #receiver}<br>
 * {@link #methodId}<br>
 * {@link #args}
 */
public class ExprCall extends Expr {
    public final Expr receiver;
    public final Ident methodId;
    public final ASTList<Expr> args;

    public ExprCall(final Expr receiver, final Ident methodId, final ASTList<Expr> args) {
        super(receiver, methodId, args);
        this.receiver = receiver;
        this.methodId = methodId;
        this.args = args;
    }

    @Override
    public void accept(final ASTVisitor v) {
        v.visit(this);
    }
}
