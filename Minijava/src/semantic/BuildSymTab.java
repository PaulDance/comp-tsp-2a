package semantic;

import main.DEBUG;
import semantic.symtab.Info;
import semantic.symtab.InfoKlass;
import semantic.symtab.InfoMethod;
import semantic.symtab.InfoVar;
import semantic.symtab.Scope;
import syntax.ast.ASTNode;
import syntax.ast.ASTVisitorDefault;
import syntax.ast.Formal;
import syntax.ast.Klass;
import syntax.ast.Method;
import syntax.ast.StmtBlock;
import syntax.ast.Var;


/** Construction de la Table de Symboles */
public class BuildSymTab extends ASTVisitorDefault {
	/**
	 * Erreur de redéfinition dans la même portée.
	 */
	private boolean error;
	/**
	 * Input / Output.
	 */
	private final SemanticTree semanticTree;
	/**
	 * Attribut hérité, sauvegardé dans semabticTree.nodeScope.
	 * == entrée dans la table des symboles pour chaque noeud.
	 */
	private Scope currentScope;
	/**
	 * Attribut hérité, usage unique pour calcul du Type de
	 * la vatiable "this".
	 */
	private InfoKlass currentKlass;
	
	// Constructeur
	public BuildSymTab(SemanticTree semanticTree) {
		this.error = false;
		this.semanticTree = semanticTree;
		this.currentScope = semanticTree.rootScope;
		this.currentKlass = null;
		
		semanticTree.axiom.accept(this);
	}
	
	@Override
	public void visit(Klass klass) {
		this.setScope(klass, this.currentScope);
		klass.klassId.accept(this);
		klass.parentId.accept(this);
		
		InfoKlass infoKlass = new InfoKlass(klass.klassId.name, klass.parentId.name);
		this.currentKlass = infoKlass;
		this.currentScope = this.newKlassScope(this.currentScope, infoKlass);
		
		klass.vars.accept(this);
		klass.methods.accept(this);
		
		this.currentKlass = null;
		this.currentScope = this.getScope(klass);
	}
	
	@Override
	public void visit(Method method) {
		this.setScope(method, this.currentScope);
		method.returnType.accept(this);
		method.methodId.accept(this);
		
		final InfoVar[] varsArray = new InfoVar[method.fargs.size() + 1];
		varsArray[0] = new InfoVar("this", this.currentKlass.getName());
		method.fargs.accept(this);
		int i = 1;
		
		for (ASTNode node: method.fargs) {
			varsArray[i] = new InfoVar(((Formal) node).varId.name, ((Formal) node).typeId.name);
			i++;
		}
		
		this.currentScope = this.newMethodScope(this.currentScope,
												new InfoMethod(method.returnType.name,
																method.methodId.name,
																varsArray));
		
		method.vars.accept(this);
		method.stmts.accept(this);
		method.returnExp.accept(this);
		
		this.currentScope = this.getScope(method);
	}
	
	@Override
	public void visit(Formal formal) {
		this.defaultVisit(formal);
	}
	
	@Override
	public void visit(Var var) {
		this.setScope(var, this.currentScope);
		var.typeId.accept(this);
		var.varId.accept(this);
		
		InfoVar v = new InfoVar(var.varId.name, var.typeId.name);
		this.checkRedef(this.currentScope.insertVariable(v));
		this.currentScope = this.getScope(var);
	}
	
	@Override
	public void visit(StmtBlock stmtBlock) {
		this.setScope(stmtBlock, this.currentScope);
		this.currentScope = new Scope(this.currentScope);
		
		stmtBlock.vars.accept(this);
		stmtBlock.stmts.accept(this);
		
		this.currentScope = this.getScope(stmtBlock);
	}
	
	/**
	 * Visite par défaut avec gestion de l'attribut hérité currentScope.
	 */
	@Override
	public void defaultVisit(ASTNode n) {
		this.setScope(n, this.currentScope);
		
		for (ASTNode f: n) {
			f.accept(this);
		}
		
		this.currentScope = this.getScope(n);
	}
	
	private void setScope(ASTNode n, Scope sc) {
		this.semanticTree.scopeAttr.set(n, sc);
	}
	
	private Scope getScope(ASTNode n) {
		return this.semanticTree.scopeAttr.get(n);
	}
	
	/**
	 * Ajouter une declaration de classe et créer une nouvelle portée.
	 */
	private Scope newKlassScope(Scope sc, InfoKlass kl) {
		this.checkRedef(sc.insertKlass(kl));
		
		sc = new Scope(sc, kl.getName());
		kl.setScope(sc);
		
		return sc;
	}
	
	/**
	 * Ajouter une declaration de Method et créer une (deux) nouvelle portée.
	 * Inclus l'ajout des paramètres formels dans la portée intermédiaire.
	 */
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
	
	/**
	 * Gestion des redéfinitions dans une même portée.
	 * NB : HashMap.add() non null => already exists.
	 */
	private void checkRedef(Info i) {
		if (i != null) {
			DEBUG.logErr("BuildSymtab : Duplication d'identificateur " + i);
			this.error = true;
		}
	}
	
	/** Sortie en erreur. */
	public boolean getError() {
		return this.error;
	}
}
