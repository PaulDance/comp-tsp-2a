package syntax.ast;

/**
 * Identificateur de type.
 * <p>
 * {@link #name}
 */
public class Type extends ASTNode {
    public final String name;

    public Type(final String name) { // types references
        this.name = name;
    }

    /** Constructeur altérnatif */
    public Type(final main.TYPE t) {  // type primitifs
        this.name = t.toString();
    }

    @Override
    public String toString() {
        return super.toString() + " " + this.name;
    }

    @Override
    public void accept(final ASTVisitor v) {
        v.visit(this);
    }
}
