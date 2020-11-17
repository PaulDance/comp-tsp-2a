package syntax.ast;

/**
 * Déclaration de variables.
 * <p>
 * {@link #typeId}<br>
 * {@link #varId}
 */
public class Var extends ASTNode {
    public final Type typeId;
    public final Ident varId;

    public Var(final Type typeId, final Ident varId) {
        super(typeId, varId);
        this.typeId = typeId;
        this.varId = varId;
    }

    @Override
    public void accept(final ASTVisitor v) {
        v.visit(this);
    }
}
