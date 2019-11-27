package syntax.ast;

/**
 * Déclaration de variables.
 * <p>
 * {@link #typeId}<br>
 * {@link #varId}
 */
public class Var extends ASTNode {
	public final Type typeId;
	public final Ident varId;
	
	public Var(Type typeId, Ident varId) {
		super(typeId, varId);
		this.typeId = typeId;
		this.varId = varId;
	}
	
	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}
