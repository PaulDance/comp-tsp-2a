package semantic;

import semantic.symtab.InfoKlass;
import semantic.symtab.InfoVar;
import semantic.symtab.Scope;
import syntax.ast.ASTNode;
import syntax.ast.ASTVisitorDefault;
import syntax.ast.ExprLiteralInt;
import syntax.ast.StmtPrint;


/**
 * Contrôle de Type.
 * <p>
 * Calcule l'attribut synthétisé Type : requis pour les noeuds Expr*
 * <p>
 * Verifie les contraintes de Typage de minijava
 */
public class TypeChecking extends ASTVisitorDefault {
	// Primitive Type names in Minijava
	private static final String BOOL = main.TYPE.BOOL.toString();
	private static final String INT = main.TYPE.INT.toString();
	private static final String INT_ARRAY = main.TYPE.INT_ARRAY.toString();
	private static final String VOID = main.TYPE.UNDEF.toString();
	/**
	 * Erreur de redéfinition dans la même portée.
	 */
	private boolean error;
	private final SemanticTree semanticTree;
	
	public TypeChecking(SemanticTree semanticTree) {
		this.error = false;
		this.semanticTree = semanticTree;
		semanticTree.axiom.accept(this);
	}
	
	/////////////////// Visit ////////////////////
	// Visites spécifiques : (non defaultVisit)
	// - Expr* : set Type
	// - Stmt* + Expr* (sauf exceptions) : Compatibilité des Types
	// - Type : Validité des noms de type dans déclarations ( Var, Method,
	/////////////////// Formal)
	// - Method : returnType compatible avec Type(returnExpr)
	// NB : validité des declarations de classe pré-requis (checkInheritance)
	@Override
	public void visit(ExprLiteralInt litInt) {
		this.setType(litInt, INT);
	}
	
	@Override
	public void visit(StmtPrint stmtPrint) {
		this.defaultVisit(stmtPrint);
		this.checkType(INT, this.getType(stmtPrint.expr), "non integer for printing", stmtPrint);
	}
	
	private String getType(ASTNode node) {
		return this.semanticTree.typeAttr.get(node);
	}
	
	private void setType(ASTNode node, String type) {
		this.semanticTree.typeAttr.set(node, type);
	}
	
	private Scope getScope(ASTNode node) {
		return this.semanticTree.scopeAttr.get(node);
	}
	
	private InfoKlass lookupKlass(String name) {
		return this.semanticTree.rootScope.lookupKlass(name);
	}
	
	/**
	 * Compare type: returns true if t2 is subtype of t1.
	 */
	private boolean compareType(String t1, String t2) {
		if (t2 == null) {
			return false;
		}
		else if (t2.equals(t1)) {
			return true;
		}
		else {							// Sinon (t1 ancêtre de t2) ?
			InfoKlass kl2 = this.lookupKlass(t2);
			
			if (kl2 != null) {
				return this.compareType(t1, kl2.getParent());
			}
			
			return false;				// NB : Suppose heritage valide !!!
		}
	}
	
	/**
	 * Report an error.
	 */
	private void reportError(ASTNode where, String msg) {
		main.DEBUG.logErr(where + " " + msg);
		this.error = true;
	}
	
	/**
	 * Validation: "t2 sub-type of t1".
	 */
	private void checkType(String t1, String t2, String msg, ASTNode where) {
		if (!this.compareType(t1, t2)) {
			this.reportError(where, "Wrong Type : " + t2 + "->" + t1 + ";  " + msg);
		}
	}
	
	/**
	 * Validation: TypeName is a valid Type.
	 */
	private void checkTypeName(String type, ASTNode where) {
		if (type.equals(BOOL) || type.equals(INT) || type.equals(INT_ARRAY)
				|| type.equals(VOID)|| this.lookupKlass(type) != null) {
			return;
		}
		
		this.reportError(where, "Unknown Type : " + type);
	}
	
	/**
	 * Lookup symbolTable for variable type.
	 */
	private String lookupVarType(ASTNode n, String name) {
		InfoVar v = this.getScope(n).lookupVariable(name);
		
		if (v == null) {
			return VOID;
		}
		else {
			return v.getType();
		}
	}
	
	/** Sortie en erreur. */
	public boolean getError() {
		return this.error;
	}
}
