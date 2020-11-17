package syntax.ast;

/**
 * Affectation d'un élément de tableau.
 * <p>
 * {@link #arrayId}<br>
 * {@link #index}<br>
 * {@link #value}
 */
public class StmtArrayAssign extends Stmt {
    public final Ident arrayId;
    public final Expr index;
    public final Expr value;

    public StmtArrayAssign(final Ident arrayId, final Expr index, final Expr value) {
        super(arrayId, index, value);
        this.arrayId = arrayId;
        this.index = index;
        this.value = value;
    }

    @Override
    public void accept(final ASTVisitor v) {
        v.visit(this);
    }
}
