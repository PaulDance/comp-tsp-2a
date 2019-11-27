package intermediate;

import java.util.List;
import java.util.ArrayList;
import semantic.SemanticTree;
import semantic.symtab.Scope;
import intermediate.ir.*;

/** Représenttion Intermédiaire :
 * <ul><li>Programe : sequence d'instructions (IRQuadruple)
 * <li>Table des symboles de l'AST
 * <li>Table des variables IR : labels, constantes, variables temporaires </ul>
 */
public class IR {
  /** Programe intermédiaire = sequence d'instructions (IRQuadruple) */
  public final List<IRQuadruple> program;
  /** Racine de la table des symboles AST. */
  public final Scope rootScope;
  // Table des symboles IR : Variables Label, constante, temporaire
  /** Liste des Variables Tempos */
  public final List<IRTempo> tempos; 
  /** Liste des Constantes */
  public final List<IRConst> consts;
  /** Liste des Labels */
  public final List<IRLabel> labels; 
  
  /** Création Vatiable Temporaire IR.
   * Nom de variable autogénéré 
   * @param scope Methode courante utilisée comme scope pour allocation
   */
  public IRVar newTemp(String scope) {
    IRTempo v = new IRTempo(scope);  tempos.add(v); return v;
  }
  /** Création Constante IR (integer litteral) 
   * Nom de la variable = value.toString() 
   * @param value Valeur entière  
   */
  public IRVar newConst(int value) {
    IRConst v = new IRConst(value); consts.add(v); return v;
  }
  /** Création Label Temporaire 
   * Nom de label autogénéré 
   */
  public IRVar newLabel() {
    IRLabel v = new IRLabel(); labels.add(v); return v;
  }
  /** Création Label de Methode 
   * @param name Nom du label
   */
  public IRVar newLabel(String name) {
    IRLabel v = new IRLabel(name); labels.add(v); return v;
  }
  
  // ToBeDone integrate tempos in ASTsymtab ??...

  /** Constructeur */
  public IR(SemanticTree semanticTree) {
    this.program = new ArrayList<>();
    this.rootScope = semanticTree.rootScope; 
    this.tempos = new ArrayList<>();
    this.labels = new ArrayList<>();
    this.consts = new ArrayList<>();
  }

  /** Debug */
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (IRQuadruple q : program) {
      if (!(q instanceof QLabel))
        sb.append('\t');
      sb.append(q).append(System.lineSeparator());
    }
    sb.append("= IR Tempos : ").append(tempos).append(System.lineSeparator());
    sb.append("= IR Labels : ").append(labels).append(System.lineSeparator());
    sb.append("= IR Consts : ").append(consts);
    return sb.toString();
  }
}
