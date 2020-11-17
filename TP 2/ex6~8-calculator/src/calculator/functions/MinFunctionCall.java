package calculator.functions;

import java.util.List;

import calculator.Expression;


public class MinFunctionCall extends FunctionCall {
    public MinFunctionCall(final List<Expression> argsList) throws IllegalArgumentException {
        super(argsList, FunctionCall.AT_LEAST_ONE_ARG);
    }

    @Override
    public Double evaluate() {
        Double min = Double.POSITIVE_INFINITY, tmp;

        for (final Expression expression: this.argsList) {
            tmp = expression.evaluate();

            if (tmp < min) {
                min = tmp;
            }
        }

        return min;
    }

    @Override
    public String getName() {
        return "min";
    }
}
