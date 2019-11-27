package syntax.ast;

/**
 * Déclaration de classe.
 * <p>
 * {@link #klassId}<br>
 * {@link #parentId}<br>
 * {@link #vars}<br>
 * {@link #methods}
 */
public class Klass extends ASTNode {
	/** Nom de classe */
	public final Ident klassId;
	/** Classe mère, défaut="Object" */
	public final Ident parentId;
	/** Liste des champs */
	public final ASTList<Var> vars;
	/** Liste des méthodes */
	public final ASTList<Method> methods;
	
	public Klass(Ident klassId, Ident parentId, ASTList<Var> vars, ASTList<Method> methods) {
		super(klassId, parentId, vars, methods);
		this.klassId = klassId;
		
		if (parentId == null) {
			parentId = new Ident("Object");
		}
		
		this.parentId = parentId;
		this.vars = vars;
		this.methods = methods;
	}
	
	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}
