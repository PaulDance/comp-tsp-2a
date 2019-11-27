package intermediate;

import main.DEBUG;
import intermediate.ir.*;
import syntax.ast.*;
import semantic.SemanticAttribut;
import semantic.SemanticTree;

/** Générarion de la forme intermédiaire (Code à 3 adresses).  */
public class Intermediate extends ASTVisitorDefault {
  // Input : AST Décoré et Table de symbol AST
  private final SemanticTree semanticTree;

  /** Stucture de donnée en sortie de la génératrion de code intermédiaire */
  public IR getResult() { return ir; }
  private final IR ir;

  // Attribut synthetisee nodeVar = Variable IR Temp pour resultat des expression
  private final SemanticAttribut<IRVar> varAttr; // nom Variables dans IR
  private IRVar getVar(ASTNode n) { return varAttr.get(n); }
  private IRVar setVar(ASTNode n, IRVar var) { return varAttr.set(n, var); }
 
  // Attribut synthétisé utilisé comme portée pour les variables IRtemp
  private String currentMethod;

  /** Constructeur */
  public Intermediate(SemanticTree semanticTree) {
    this.semanticTree = semanticTree;
    this.ir = new IR(semanticTree);
    this.varAttr = new SemanticAttribut<>();
    this.currentMethod=null;
    semanticTree.axiom.accept(this); // => visite((Raxiome)axiome)
    if (DEBUG.INTERMED)
      DEBUG.log(ir);
  }

  //// Helpers
  // Ajouter une instruction au programmeIR
  private void add(IRQuadruple irq) { ir.program.add(irq); }
 
  // Variables IR : label tempo, label nom, Constante, Temp var
  private IRVar newLabel() { return ir.newLabel(); }
  private IRVar newLabel(String name) { return ir.newLabel(name); }
  private IRVar newConst(int value) { return ir.newConst(value); }
  private IRVar newTemp() { return ir.newTemp(currentMethod); }

  // Variable de l'AST depuis la table de symbole
  private IRVar lookupVar(String name, ASTNode n) {
    return semanticTree.scopeAttr.get(n).lookupVariable(name);
  }

  /////////////////// Visit ////////////////////
  @Override
  public void visit(KlassMain n) {
    currentMethod="main";
    add(new QLabel(newLabel(currentMethod)));
    defaultVisit(n);
    add(new QCall(newLabel("_system_exit"), newConst(0), null));
    currentMethod=null;
  }

  @Override
  public void visit(ExprLiteralInt n) {
    setVar(n, newConst(n.value));
  }

  @Override
  public void visit(StmtPrint n) {
    defaultVisit(n);
    add(new QParam(getVar(n.expr)));
    add(new QCall(newLabel("_system_out_println"), newConst(1), null));
  }


}
