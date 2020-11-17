package syntax.ast;

/**
 * Paramètre formel de méthode.
 * <p>
 * {@link #typeId}<br>
 * {@link #varId}
 */
public class Formal extends ASTNode {
    /** Nom de Type */
    public final Type typeId;
    /** Nom de Variable */
    public final Ident varId;

    public Formal(final Type typeId, final Ident varId) {
        super(typeId, varId);
        this.typeId = typeId;
        this.varId = varId;
    }

    @Override
    public void accept(final ASTVisitor v) {
        v.visit(this);
    }
}
