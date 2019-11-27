package syntax.ast;

/** Visiteur générique de l'AST avec parcours en profondeur. */
public class ASTVisitorDefault implements ASTVisitor {
  /** parcours récursif en profondeur. */
  public void defaultVisit(ASTNode node) {
    for (ASTNode f : node)
      f.accept(this);
  }
  
  // Liste homogéne, extends ASTNode
  public <T extends ASTNode> void visit(ASTList<T> n) { defaultVisit(n); }
  // Productions de base, extends ASTNode 
  public void visit(Axiom n)           { defaultVisit(n); }
  public void visit(Klass n)           { defaultVisit(n); }
  public void visit(KlassMain n)       { defaultVisit(n); }
  public void visit(Method n)          { defaultVisit(n); }
  public void visit(Formal n)          { defaultVisit(n); }
  public void visit(Ident n)           { defaultVisit(n); }
  public void visit(Type n)            { defaultVisit(n); }
  public void visit(Var n)             { defaultVisit(n); }
 // Expressions , extends Expr
  public void visit(ExprArrayLength n) { defaultVisit(n); }
  public void visit(ExprArrayLookup n) { defaultVisit(n); }
  public void visit(ExprArrayNew n)    { defaultVisit(n); }
  public void visit(ExprCall n)        { defaultVisit(n); }
  public void visit(ExprIdent n)       { defaultVisit(n); }
  public void visit(ExprLiteralBool n) { defaultVisit(n); }
  public void visit(ExprLiteralInt n)  { defaultVisit(n); }
  public void visit(ExprNew n)         { defaultVisit(n); }
  public void visit(ExprOpBin n)       { defaultVisit(n); }
  public void visit(ExprOpUn n)        { defaultVisit(n); }
  // Instructions, extends Stmt
  public void visit(StmtArrayAssign n) { defaultVisit(n); }
  public void visit(StmtAssign n)      { defaultVisit(n); }
  public void visit(StmtBlock n)       { defaultVisit(n); }
  public void visit(StmtIf n)          { defaultVisit(n); }
  public void visit(StmtPrint n)       { defaultVisit(n); }
  public void visit(StmtWhile n)       { defaultVisit(n); }
}
