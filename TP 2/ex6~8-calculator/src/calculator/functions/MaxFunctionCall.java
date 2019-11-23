package calculator.functions;

import java.util.List;

import calculator.Expression;


public class MaxFunctionCall extends FunctionCall {
	public MaxFunctionCall(final List<Expression> argsList) throws IllegalArgumentException {
		super(argsList, FunctionCall.AT_LEAST_ONE_ARG);
	}
	
	@Override
	public Double evaluate() {
		Double max = Double.NEGATIVE_INFINITY, tmp;
		
		for (Expression expression: this.argsList) {
			tmp = expression.evaluate();
			
			if (tmp > max) {
				max = tmp;
			}
		}
		
		return max;
	}
	
	@Override
	public String getName() {
		return "max";
	}
}
