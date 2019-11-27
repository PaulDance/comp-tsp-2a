package semantic;

import java.util.HashMap;
import java.util.Map;

import syntax.ast.ASTNode;


/**
 * Décoration de l'AST : Attributs Sémantiques. <br>
 * Utilise une structure Map<ASTNode,R> pour eviter de modifier l'AST existant.
 * <p>
 * Usefull to :
 * <ul>
 * <li>manage return value with (void) visitor (bottom-up Attributs)
 * <li>avoid paramaters in visitor (top-down Attributs)
 * <li>reuse Attributs between several visits (avoiding unconsistancy)
 * </ul>
 */
public class SemanticAttribut<R> {
	private final Map<ASTNode, R> attribut;
	
	public SemanticAttribut() {
		this.attribut = new HashMap<>();
	}
	
	public R get(ASTNode n) {
		return this.attribut.get(n);
	}
	
	public R set(ASTNode n, R attr) {
		return this.attribut.put(n, attr);
	}
}
