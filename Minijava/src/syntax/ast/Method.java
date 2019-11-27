package syntax.ast;

/** Déclaration de méthode.
  <p>{@link #returnType}<br>{@link #methodId}<br>{@link #fargs}
  <br>{@link #vars}<br>{@link #stmts}<br>{@link #returnType}
 */
public class Method extends ASTNode {
  /** Type de retour */
  public final Type returnType;
  /** Nom de méthode */
  public final Ident methodId;
  /** Liste des paramètres formels */
  public final ASTList<Formal> fargs;
  /** Liste des variables locales */
  public final ASTList<Var> vars;
  /** Liste des instructions */
  public final ASTList<Stmt> stmts;
  /** Argument du return */
  public final Expr returnExp; 

  public Method(Type returnType, Ident methodId, ASTList<Formal> fargs,
      ASTList<Var> vars, ASTList<Stmt> stmts, Expr returnExp) {
    super(returnType, methodId, fargs, vars, stmts, returnExp);
    this.returnType = returnType;
    this.methodId = methodId;
    this.fargs = fargs; 
    this.vars = vars;
    this.stmts = stmts;
    this.returnExp = returnExp;
  }

  public void accept(ASTVisitor v) { v.visit(this); }
}
