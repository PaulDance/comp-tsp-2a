package syntax.ast;

/**
 * Opérateur Unaire.
 * <p>
 * {@link #op}<br>
 * {@link #expr}
 */
public class ExprOpUn extends Expr {
	public final main.OPER op;
	public final Expr expr;
	
	public ExprOpUn(main.OPER op, Expr expr) {
		super(expr);
		this.op = op;
		this.expr = expr;
	}
	
	@Override
	public String toString() {
		return super.toString() + " " + this.op.name();
	}
	
	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}
