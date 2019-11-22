
public abstract class Expression extends ASTNode {
	public Expression(ASTNode... fils) {
		super(fils);
	}
	
	@Override
	public void accept(ASTVisitor v) {}
	
	public abstract Double evaluate();
}
