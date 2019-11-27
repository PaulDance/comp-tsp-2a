package syntax.ast;

/**
 * Accès à l'élément d'un tableau.
 * <p>
 * {@link #array}<br>
 * {@link #index}
 */
public class ExprArrayLookup extends Expr {
	public final Expr array;
	public final Expr index;
	
	public ExprArrayLookup(Expr array, Expr index) {
		super(array, index);
		this.array = array;
		this.index = index;
	}
	
	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}
