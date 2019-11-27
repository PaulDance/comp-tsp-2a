package syntax.ast;

/**
 * Instanciation d'objet.
 * <p>
 * {@link #klassId}
 */
public class ExprNew extends Expr {
	public final Ident klassId;
	
	public ExprNew(Ident klassId) {
		super(klassId);
		this.klassId = klassId;
	}
	
	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}
