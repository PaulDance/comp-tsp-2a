package calculator.functions;

import java.util.List;

import calculator.Expression;


public class CbrtFunctionCall extends FunctionCall {
	private final Expression op;
	
	public CbrtFunctionCall(final List<Expression> argsList) throws IllegalArgumentException {
		super(argsList, 1);
		this.op = this.argsList.get(0);
	}
	
	@Override
	public Double evaluate() {
		return Math.cbrt(this.op.evaluate());
	}
	
	@Override
	public String getName() {
		return "sqrt";
	}
}
