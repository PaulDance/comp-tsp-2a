package calculator.operations;

import calculator.Expression;


public class PowerOperation extends Expression {
	private final Expression op1, op2;
	
	public PowerOperation(Expression op1, Expression op2) {
		super(op1, op2);
		this.op1 = op1;
		this.op2 = op2;
	}
	
	@Override
	public Double evaluate() {
		return Math.pow(op1.evaluate(), op2.evaluate());
	}
}
