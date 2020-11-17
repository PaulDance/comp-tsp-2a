package syntax.ast;

/**
 * Identificateur (classe, methode, champs, variable).
 * <p>
 * {@link #name}
 */
public class Ident extends ASTNode {
    public final String name;

    public Ident(final String name) {
        this.name = name;
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
