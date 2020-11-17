/** Noeud AST géneérique "non typée" (== CST): String label */
public class AST extends ASTNode {
    public final String label;

    public AST(final String label, final ASTNode... fils) {
        super(fils);
        this.label = label;
    }

    @Override
    public void accept(final ASTVisitor v) {
        v.visit(this);
    }

    @Override
    public String toString() {
        return this.label;
    }
}
