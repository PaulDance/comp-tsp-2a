package intermediate;

import java.util.ArrayList;
import java.util.List;

import intermediate.ir.IRConst;
import intermediate.ir.IRLabel;
import intermediate.ir.IRQuadruple;
import intermediate.ir.IRTempo;
import intermediate.ir.IRVar;
import intermediate.ir.QLabel;
import intermediate.ir.QLabelMeth;
import semantic.SemanticTree;
import semantic.symtab.Scope;


/**
 * Représenttion Intermédiaire :
 * <ul>
 * <li>Programe : sequence d'instructions (IRQuadruple)
 * <li>Table des symboles de l'AST
 * <li>Table des variables IR : labels, constantes, variables temporaires
 * </ul>
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

    /**
     * Création Vatiable Temporaire IR. Nom de variable autogénéré
     *
     * @param scope Methode courante utilisée comme scope pour allocation
     */
    public IRVar newTemp(final String scope) {
        final IRTempo v = new IRTempo(scope);
        this.tempos.add(v);
        return v;
    }

    /**
     * Création Constante IR (integer litteral) Nom de la variable =
     * value.toString()
     *
     * @param value Valeur entière
     */
    public IRVar newConst(final int value) {
        final IRConst v = new IRConst(value);
        this.consts.add(v);
        return v;
    }

    /**
     * Création Label Temporaire Nom de label autogénéré
     */
    public IRVar newLabel() {
        final IRLabel v = new IRLabel();
        this.labels.add(v);
        return v;
    }

    /**
     * Création Label de Methode
     *
     * @param name Nom du label
     */
    public IRVar newLabel(final String name) {
        final IRLabel v = new IRLabel(name);
        this.labels.add(v);
        return v;
    }

    // ToBeDone integrate tempos in ASTsymtab ??...

    /** Constructeur */
    public IR(final SemanticTree semanticTree) {
        this.program = new ArrayList<>();
        this.rootScope = semanticTree.rootScope;
        this.tempos = new ArrayList<>();
        this.labels = new ArrayList<>();
        this.consts = new ArrayList<>();
    }

    /** Debug */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        for (final IRQuadruple q: this.program) {
            if (!(q instanceof QLabel || q instanceof QLabelMeth)) {
                sb.append('\t');
            }

            sb.append(q).append(System.lineSeparator());
        }

        sb.append("= IR Tempos : ").append(this.tempos).append(System.lineSeparator());
        sb.append("= IR Labels : ").append(this.labels).append(System.lineSeparator());
        sb.append("= IR Consts : ").append(this.consts);

        return sb.toString();
    }
}
