/** Visiteur générique de l'AST avec parcours en profondeur. */
public class ASTVisitorDefault implements ASTVisitor {
  /** parcours récursif en profondeur. */
  public void defaultVisit(ASTNode node) {
    for (ASTNode f : node)
      f.accept(this);
  }
  
  public <T extends ASTNode> void visit(ASTList<T> n) { defaultVisit(n); }

  public void visit(AST n)    { defaultVisit(n); }
  // ...
}
