package syntax.ast;

/**
 * Programme Minijava.
 * <p>
 * {@link #klassMain}<br>
 * {@link #klassList}
 */
public class Axiom extends ASTNode {
    /** Classe conventionnelle main() */
    public final KlassMain klassMain;
    /** Liste des autres classes */
    public final ASTList<Klass> klassList;

    public Axiom(final KlassMain klassMain, final ASTList<Klass> klassList) {
        super(klassMain, klassList);
        this.klassMain = klassMain;
        this.klassList = klassList;
    }

    @Override
    public void accept(final ASTVisitor v) {
        v.visit(this);
    }
}
