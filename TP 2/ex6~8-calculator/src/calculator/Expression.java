package calculator;

import ast.ASTNode;
import ast.ASTVisitor;


public abstract class Expression extends ASTNode {
	public Expression(Expression... fils) {
		super(fils);
	}
	
	@Override
	public void accept(ASTVisitor v) {}
	
	public abstract Double evaluate();
}
