package syntax.ast;

/**
 * Taille d'un tableau.
 * <p>
 * {@link #array}
 */
public class ExprArrayLength extends Expr {
	public final Expr array;
	
	public ExprArrayLength(Expr array) {
		super(array);
		this.array = array;
	}
	
	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}
