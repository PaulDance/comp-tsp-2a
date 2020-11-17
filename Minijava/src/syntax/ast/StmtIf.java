package syntax.ast;

/**
 * Instruction if then else.
 * <p>
 * {@link #test}<br>
 * {@link #ifTrue}<br>
 * {@link #ifFalse}
 */
public class StmtIf extends Stmt {
    public final Expr test;
    public final Stmt ifTrue;
    public final Stmt ifFalse;

    public StmtIf(final Expr test, final Stmt ifTrue, final Stmt ifFalse) {
        super(test, ifTrue, ifFalse);
        this.test = test;
        this.ifTrue = ifTrue;
        this.ifFalse = ifFalse;
    }

    @Override
    public void accept(final ASTVisitor v) {
        v.visit(this);
    }
}
