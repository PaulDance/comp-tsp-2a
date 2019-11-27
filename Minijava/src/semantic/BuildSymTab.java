package semantic;

import main.DEBUG;
import semantic.symtab.Info;
import semantic.symtab.InfoKlass;
import semantic.symtab.InfoMethod;
import semantic.symtab.InfoVar;
import semantic.symtab.Scope;
import syntax.ast.ASTNode;
import syntax.ast.ASTVisitorDefault;


/** Construction de la Table de Symboles */
public class BuildSymTab extends ASTVisitorDefault {
	/** Sortie en erreur. */
	public boolean getError() {
		return this.error;
	}
	
	private boolean error; // erreur de rédéfinition dans la meme portée
	
	// Input/Output
	private final SemanticTree semanticTree;
	
	// Attribut hérité, sauvegardé dans semabticTree.nodeScope
	// == entrée dans la table des symboles pour chaque noeud
	private Scope currentScope;
	
	// Attribut hérité, usage unique pour calcul du Type de la vatiable "this"
	private InfoKlass currentKlass;
	
	// Constructeur
	public BuildSymTab(SemanticTree semanticTree) {
		this.error = false;
		this.semanticTree = semanticTree;
		this.currentScope = semanticTree.rootScope;
		this.currentKlass = null;
		semanticTree.axiom.accept(this);
	}
	
	// helpers ...
	// Attribut "Scope"
	private void setScope(ASTNode n, Scope sc) {
		this.semanticTree.scopeAttr.set(n, sc);
	}
	
	private Scope getScope(ASTNode n) {
		return this.semanticTree.scopeAttr.get(n);
	}
	
	// Ajouter une declaration de classe et creer une nouvelle portée
	private Scope newKlassScope(Scope sc, InfoKlass kl) {
		this.checkRedef(sc.insertKlass(kl));
		sc = new Scope(sc, kl.getName());
		kl.setScope(sc);
		return sc;
	}
	
	// Ajouter une declaration de Method et creer une (deux) nouvelle portée
	// Inclus l'ajout des parametres formels dans la portée intermédiaire
	private Scope newMethodScope(Scope sc, InfoMethod m) {
		this.checkRedef(sc.insertMethod(m));
		sc = new Scope(sc, m.getName() + "_args");
		
		for (InfoVar v: m.getArgs()) {
			this.checkRedef(sc.insertVariable(v));
		}
		
		sc = new Scope(sc, m.getName());
		m.setScope(sc);
		return sc;
	}
	
	// Gestion des redefinitions dans une meme portée
	// NB : HashMap.add() non null => already exists
	private void checkRedef(Info i) {
		if (i != null) {
			DEBUG.logErr("BuildSymtab : Duplication d'identificateur " + i);
			this.error = true;
		}
	}
	
	////////////// Visit ////////////////////////
	// visite par défaut avec gestion de l'attribut hérité currentScope
	@Override
	public void defaultVisit(ASTNode n) {
		this.setScope(n, this.currentScope);
		
		for (ASTNode f: n) {
			f.accept(this);
		}
		
		this.currentScope = this.getScope(n);
	}
	
}
