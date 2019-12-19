package semantic;

import semantic.symtab.InfoKlass;
import semantic.symtab.InfoMethod;
import semantic.symtab.InfoVar;
import semantic.symtab.Scope;
import syntax.ast.ASTNode;


/**
 * <b>Arbre Sémantique</b> == AST + Attributs de Noeuds + Table de Symboles
 * <p>
 * <b>Attributs hérités ou descendants :</b> <br>
 * <b>Scope</b> : portée courante dans la table de symboles
 * <p>
 * <b>Attributs synthétisés ou remontants : </b> <br>
 * <b>Type</b> : type de données pour les expressions
 */
public class SemanticTree {
	/**
	 * Racine de l'AST. <br>
	 * assert(axiom instanceOf Axiom)
	 */
	public final ASTNode axiom;
	
	/**
	 * Racine de la table des symboles.
	 * <p>
	 * Utile comme entrée dans la table des classes
	 * <p>
	 * assert( rootScope=axiom.getScope()
	 */
	public final Scope rootScope;
	
	/** Attribut sémantique Scope. Entrée de la table des symboles d'un noeud */
	public final SemanticAttribut<Scope> scopeAttr;
	
	/** Attribut sémantique Type. Type de données pour noeuds Pexpr* */
	public final SemanticAttribut<String> typeAttr;
	
	/** Constructeur */
	public SemanticTree(ASTNode axiom) {
		this.axiom = axiom;
		this.rootScope = new Scope(null, "Root");
		this.scopeAttr = new SemanticAttribut<Scope>();
		this.typeAttr = new SemanticAttribut<String>();
		this.addObjectKlass();
	}
	
	// create an object class
	private void addObjectKlass() {
		Scope sc = this.rootScope;
		// Add an Object class (requis)
		InfoKlass kl = new InfoKlass("Object", null);
		sc.insertKlass(kl);
		sc = new Scope(sc, kl.getName());
		kl.setScope(sc);
		// Add a method for testing
		InfoMethod m = new InfoMethod("boolean", "equals", new InfoVar("this", kl.getName()),
				new InfoVar("o", kl.getName()));
		sc.insertMethod(m);
		sc = new Scope(sc, m.getName() + "_args");
		
		for (InfoVar v: m.getArgs()) {
			sc.insertVariable(v);
		}
		
		sc = new Scope(sc, m.getName());
		m.setScope(sc);
	}
}
