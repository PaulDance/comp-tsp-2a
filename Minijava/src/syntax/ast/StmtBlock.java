package syntax.ast;

/**
 * Bloc d'instructions.
 * <p>
 * {@link #vars}<br>
 * {@link #stmts}
 */
public class StmtBlock extends Stmt {
    /** Liste des variables locales */
    public final ASTList<Var> vars;
    /** Liste des Instructions */
    public final ASTList<Stmt> stmts;

    public StmtBlock(final ASTList<Var> vars, final ASTList<Stmt> stmts) {
        super(vars, stmts);
        this.vars = vars;
        this.stmts = stmts;
    }

    @Override
    public void accept(final ASTVisitor v) {
        v.visit(this);
    }
}
