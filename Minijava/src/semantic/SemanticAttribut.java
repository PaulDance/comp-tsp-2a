package semantic;

import java.util.HashMap;
import java.util.Map;

import syntax.ast.ASTNode;


/**
 * Décoration de l'AST : Attributs Sémantiques. <br>
 * Utilise une structure Map<ASTNode,R> pour éviter de modifier l'AST existant.
 * <p>
 * Useful to :
 * <ul>
 * <li>manage return value with (void) visitor (bottom-up Attributs)
 * <li>avoid parameters in visitor (top-down Attributs)
 * <li>reuse Attributs between several visits (avoiding inconsistency)
 * </ul>
 */
public class SemanticAttribut<R> {
	private final Map<ASTNode, R> attribut;
	
	public SemanticAttribut() {
		this.attribut = new HashMap<>();
	}
	
	public R get(ASTNode node) {
		return this.attribut.get(node);
	}
	
	public R set(ASTNode node, R attr) {
		return this.attribut.put(node, attr);
	}
}
