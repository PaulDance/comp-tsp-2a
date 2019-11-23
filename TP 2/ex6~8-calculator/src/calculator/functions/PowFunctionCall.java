package calculator.functions;

import java.util.List;

import calculator.Expression;


public class PowFunctionCall extends FunctionCall {
	private final Expression op1, op2;
	
	public PowFunctionCall(final List<Expression> argsList) throws IllegalArgumentException {
		super(argsList, 2);
		this.op1 = argsList.get(0);
		this.op2 = argsList.get(1);
	}
	
	@Override
	public Double evaluate() {
		return Math.pow(op1.evaluate(), op2.evaluate());
	}
	
	@Override
	public String getName() {
		return "pow";
	}
}
