package ast;

/**
 * Visiteur générique de l'AST avec parcours en profondeur.
 */
public class ASTVisitorDefault implements ASTVisitor {
    /**
     * Parcours récursif en profondeur.
     */
    public void defaultVisit(final ASTNode node) {
        for (final ASTNode f: node) {
            f.accept(this);
        }
    }

    @Override
    public <T extends ASTNode> void visit(final ASTList<T> n) {
        this.defaultVisit(n);
    }

    @Override
    public void visit(final AST n) {
        this.defaultVisit(n);
    }
}
