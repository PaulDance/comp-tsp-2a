package calculator.functions;

import java.util.List;

import calculator.Expression;


public class MaxFunctionCall extends FunctionCall {
	public MaxFunctionCall(List<Expression> argsList) {
		super(argsList);
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
}
