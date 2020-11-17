package calculator;

import ast.ASTNode;
import ast.ASTVisitor;


public abstract class Expression extends ASTNode {
    public Expression(final Expression... fils) {
        super(fils);
    }

    @Override
    public void accept(final ASTVisitor v) {}

    public abstract Double evaluate();
}
