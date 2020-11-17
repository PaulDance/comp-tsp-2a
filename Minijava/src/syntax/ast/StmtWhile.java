package syntax.ast;

/**
 * Boucle While.
 * <p>
 * {@link #test}<br>
 * {@link #body}
 */
public class StmtWhile extends Stmt {
    public final Expr test;
    public final Stmt body;

    public StmtWhile(final Expr test, final Stmt body) {
        super(test, body);
        this.test = test;
        this.body = body;
    }

    @Override
    public void accept(final ASTVisitor v) {
        v.visit(this);
    }
}
