package syntax.ast;

/** Programme Minijava. <p>{@link #klassMain}<br>{@link #klassList} */
public class Axiom extends ASTNode {
  /** Classe conventionnelle main() */ 
  public final KlassMain klassMain; 
  /** Liste des autres classes */
  public final ASTList<Klass> klassList;
 
  public Axiom(KlassMain klassMain, ASTList<Klass> klassList) {
    super(klassMain, klassList);
    this.klassMain = klassMain;
    this.klassList = klassList;
  }

  public void accept(ASTVisitor v) { v.visit(this); }
}
