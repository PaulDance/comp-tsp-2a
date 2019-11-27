package syntax.ast;

/**
 * Identificateur de Variable.
 * <p>
 * {@link #varId}
 */
public class ExprIdent extends Expr {
	public final Ident varId;
	
	public ExprIdent(Ident varId) {
		super(varId);
		this.varId = varId;
	}
	
	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}
