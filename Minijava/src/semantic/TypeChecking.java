package semantic;

import semantic.symtab.*;
import syntax.ast.*;

/** Contrôle de Type.
 * <p>Calcule l'attribut synthétisé Type : requis pour les noeuds Expr*
 * <p>Verifie les contraintes de Typage de minijava
 */
public class TypeChecking extends ASTVisitorDefault {
  /** Sortie en erreur. */
  public boolean getError() { return error; }
  private boolean error; // erreur de rédéfinition dans la meme portée
  
  // input
  private final SemanticTree semanticTree;
  
  public TypeChecking(SemanticTree semanticTree) {
    this.error = false;
    this.semanticTree = semanticTree;
    semanticTree.axiom.accept(this);
  }

  // //// Helpers
  private String getType(ASTNode n) { return semanticTree.typeAttr.get(n); }
  private void setType(ASTNode n, String type) { semanticTree.typeAttr.set(n, type); }
  private Scope getScope(ASTNode n) { return semanticTree.scopeAttr.get(n);}
  private InfoKlass lookupKlass(String name){ return semanticTree.rootScope.lookupKlass(name);}
  
  // Primitive Type names in Minijava
  private static final String BOOL = main.TYPE.BOOL.toString();
  private static final String INT = main.TYPE.INT.toString();
  private static final String INT_ARRAY = main.TYPE.INT_ARRAY.toString();
  private static final String VOID = main.TYPE.UNDEF.toString();

  //// helpers
  
  // Compare type : returns true if t2 is subtype of t1
  private boolean compareType(String t1, String t2) {
    if (t2 == null) return false;
    if (t2.equals(t1)) return true;
    // sinon (t1 ancetre de t2) ?
    InfoKlass kl2 = lookupKlass(t2);
    if (kl2 != null) return compareType(t1,kl2.getParent());
    return false;
    // NB : Suppose heritage valide !!!
   }

  // repport error
  private void erreur(ASTNode where, String msg) {
    main.DEBUG.logErr(where + " " + msg);
    error = true;
  }

  // Validation : "t2 subtype of t1"
  private void checkType(String t1, String t2, String msg, ASTNode where) {
    if (!compareType(t1, t2) )
      erreur(where, "Wrong Type : " + t2 + "->" + t1 + ";  " + msg);
  }
  
  // Validation : TypeName is a valid Type
  private void checkTypeName(String type, ASTNode where) {
    if ( type.equals(BOOL)
        || type.equals(INT)
        || type.equals(INT_ARRAY)
        || type.equals(VOID)
        || ( lookupKlass(type)!=null )
        ) return;
    erreur(where, "Unknown Type : " + type);
  }

  // lookup symbolTable for variable type
  private String lookupVarType(ASTNode n , String name) {
    InfoVar v = getScope(n).lookupVariable(name);
    if (v==null) return VOID;
    else return v.getType();
  }

  /////////////////// Visit ////////////////////
  // Visites spécifiques : (non defaultvisit)
  //  - Expr* : set Type
  //  - Stmt* + Expr* (sauf exceptions) : Compatibilité des Types
  //  - Type : Validite des noms de type dans céclarations ( Var, Method, Formal)
  //  - Method : returnType compatible avec Type(returnExpr)
  // NB : validité des declarations de classe prérequis (checkInheritance)
  @Override
  public void visit(ExprLiteralInt n) {
    setType(n, INT);
  }

  @Override
  public void visit(StmtPrint n) {
    defaultVisit(n);
    checkType(INT, getType(n.expr), "non integer for printing", n);
  }

}
