import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java_cup.runtime.ComplexSymbolFactory.Location;


/**
 * Classe abstraite ancêtre pour AST.
 * <p>
 * Construction VarArgs <br>
 * Classe itérable : {@code for (ASTNode fils : pere) {...} } <br>
 * Impression de l'arbre : {@link #toPrint()} <br>
 * Gestion des références dans le source :
 * {@link #addPosition(Location, Location)}
 */
public abstract class ASTNode implements Iterable<ASTNode> {
    public abstract void accept(ASTVisitor v);

    private final String label;
    private final List<ASTNode> fils; /* only for ASTList ! */

    /**
     * Constructeur avec VarArgs
     * 
     * @param fils liste quelconque de fils
     */
    public ASTNode(final ASTNode... fils) {
        this.label = this.getClass().getSimpleName();
        this.fils = new ArrayList<>();

        for (final ASTNode f: fils) {
            this.fils.add(f);
        }
    }

    /** Construction itérative (uniquement pour ASTList */
    protected void addFils(final ASTNode f) {
        this.fils.add(f);
    }

    /**
     * Iteration sur les fils. <br>
     * i.e. {@code ASTNode p; for (ASTNode f : p) {}}
     */
    @Override
    public Iterator<ASTNode> iterator() {
        return this.fils.iterator();
    }

    /** @return Nombre de fils */
    public int size() {
        return this.fils.size();
    }

    /** Impression Noeud */
    @Override
    public String toString() {
        return this.label + this.toLocationString();
    }

    /** Impression Arbre */
    public String toPrint() {
        final StringBuilder sb = new StringBuilder("");
        this.print("", sb);
        return sb.toString();
    }

    private static final String[][] GRAPH = {{"|-", "| ", "\\-", "  "}, // ASCII
            {"|--", "|  ", "\\--", "   "}, // ASCII Large
            {"\u251c", "\u2502", "\u2514", " "}, // Unicode
            {"\u251c\u2500", "\u2502 ", "\u2514\u2500", "  "} // Unicode Large
    };
    private static final String[] GR = ASTNode.GRAPH[3];

    private void print(final String indent, final StringBuilder sb) {
        sb.append(this).append(System.lineSeparator());
        for (final Iterator<ASTNode> it = this.iterator(); it.hasNext();) {
            final ASTNode f = it.next();
            sb.append(indent);
            if (it.hasNext()) {
                sb.append(ASTNode.GR[0]);
                f.print(indent + ASTNode.GR[1], sb);
            } else {
                sb.append(ASTNode.GR[2]);
                f.print(indent + ASTNode.GR[3], sb);
            }
        }
    }

    /**
     * Gestion optionnelle des positions des symboles dans le source.
     * <p>
     * Utilisation dans CUP : <br>
     * {@code x := a:aa .. z:zz <br>
     * {: RESULT = new Node(a,..,z); RESULT.addPosition(aaxleft,zzxright); :} }
     */
    public void addPosition(final Location left, final Location right) {
        this.start = left;
        this.stop = right;
    }

    private Location start = null;
    private Location stop = null;

    private String locStr(final Location l) {
        if (l == null) {
            return "";
        }
        return l.getLine() + "/" + l.getColumn() + "(" + l.getOffset() + ")";
    }

    private String toLocationString() {
        return "[" + this.locStr(this.start) + "-" + this.locStr(this.stop) + "]";
    }
}
