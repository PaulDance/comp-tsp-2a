package calculator.functions;

import java.util.List;

import calculator.Expression;


public class SqrtFunctionCall extends FunctionCall {
    private final Expression op;

    public SqrtFunctionCall(final List<Expression> argsList) throws IllegalArgumentException {
        super(argsList, 1);
        this.op = this.argsList.get(0);
    }

    @Override
    public Double evaluate() {
        return Math.sqrt(this.op.evaluate());
    }

    @Override
    public String getName() {
        return "sqrt";
    }
}
