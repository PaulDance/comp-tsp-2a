package calculator.functions;

import java.util.List;

import calculator.Expression;


public abstract class FunctionCall extends Expression {
	protected List<Expression> argsList;
	
	public FunctionCall(List<Expression> argsList) {
		super(argsList.toArray(new Expression[0]));
		this.argsList = argsList;
	}
}
