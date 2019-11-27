package syntax.ast;

/**
 * Constante Booléenne.
 * <p>
 * {@link #value}
 */
public class ExprLiteralBool extends Expr {
	public final Boolean value;
	
	public ExprLiteralBool(Boolean value) {
		this.value = value;
	}
	
	/** Constructeur altérnatif */
	public ExprLiteralBool(String s) {
		this.value = Boolean.parseBoolean(s);
	}
	
	@Override
	public String toString() {
		return super.toString() + " " + this.value;
	}
	
	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}
