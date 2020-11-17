package ast;

/**
 * Interface Visiteur pour AST
 */
public interface ASTVisitor {
    <T extends ASTNode> void visit(ASTList<T> n);
    void visit(AST n);
}
