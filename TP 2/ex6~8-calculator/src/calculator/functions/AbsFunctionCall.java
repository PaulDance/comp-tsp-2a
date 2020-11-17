package calculator.functions;

import java.util.List;

import calculator.Expression;


public class AbsFunctionCall extends FunctionCall {
    private final Expression op;

    public AbsFunctionCall(final List<Expression> argsList) throws IllegalArgumentException {
        super(argsList, 1);
        this.op = this.argsList.get(0);
    }

    @Override
    public Double evaluate() {
        return Math.abs(this.op.evaluate());
    }

    @Override
    public String getName() {
        return "abs";
    }
}
