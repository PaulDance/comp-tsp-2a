package syntax.ast;

/**
 * ASTNode avec fils homogènes. <br>
 * Construction itérative : {@link #add( R node)}
 */
public class ASTList<R extends ASTNode> extends ASTNode {
	/**
	 * Construction iterative
	 * 
	 * @param node noeud ajouté en fin de liste
	 */
	public void add(R node) {
		this.addFils(node);
	}
	
	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}
