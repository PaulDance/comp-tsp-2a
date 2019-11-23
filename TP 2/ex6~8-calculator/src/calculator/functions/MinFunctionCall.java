package calculator.functions;

import java.util.List;

import calculator.Expression;


public class MinFunctionCall extends FunctionCall {
	public MinFunctionCall(List<Expression> argsList) {
		super(argsList);
	}
	
	@Override
	public Double evaluate() {
		Double min = Double.POSITIVE_INFINITY, tmp;
		
		for (Expression expression: this.argsList) {
			tmp = expression.evaluate();
			
			if (tmp < min) {
				min = tmp;
			}
		}
		
		return min;
	}
}
