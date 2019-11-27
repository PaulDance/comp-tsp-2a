package syntax.ast;

/**
 * Affectation d'une variable.
 * <p>
 * {@link #varId}<br>
 * {@link #value}
 */
public class StmtAssign extends Stmt {
	public final Ident varId;
	public final Expr value;
	
	public StmtAssign(Ident varId, Expr value) {
		super(varId, value);
		this.varId = varId;
		this.value = value;
	}
	
	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}
