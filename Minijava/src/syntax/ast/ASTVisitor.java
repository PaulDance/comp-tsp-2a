package syntax.ast;

/** Interface Visiteur pour l'AST Minijava. */
public interface ASTVisitor {
  // Liste homogéne, extends ASTNode
  <T extends ASTNode> void visit(ASTList<T> n);
  // Productions de base, extends ASTNode
  void visit(Axiom n);
  void visit(Klass n);
  void visit(KlassMain n);
  void visit(Method n);
  void visit(Formal n);
  void visit(Ident n);
  void visit(Type n);
  void visit(Var n);
// Expressions , extends Expr
  void visit(ExprArrayLength n);
  void visit(ExprArrayLookup n);
  void visit(ExprArrayNew n);
  void visit(ExprCall n);
  void visit(ExprIdent n);
  void visit(ExprLiteralBool n);
  void visit(ExprLiteralInt n);
  void visit(ExprNew n);
  void visit(ExprOpBin n);
  void visit(ExprOpUn n);
  // Instructions, extends Stmt
  void visit(StmtArrayAssign n);
  void visit(StmtAssign n);
  void visit(StmtBlock n);
  void visit(StmtIf n);
  void visit(StmtPrint n);
  void visit(StmtWhile n);
}
