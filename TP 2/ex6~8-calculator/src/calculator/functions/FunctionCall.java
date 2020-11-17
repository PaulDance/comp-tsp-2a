package calculator.functions;

import java.util.List;

import calculator.Expression;


public abstract class FunctionCall extends Expression {
    public static final byte ANY_ARGS_COUNT = -1, AT_LEAST_ONE_ARG = -2;
    protected final List<Expression> argsList;
    protected final int expectedArgsCount;

    public FunctionCall(final List<Expression> argsList, final int expectedArgsCount)
            throws IllegalArgumentException {
        super(argsList.toArray(new Expression[0]));
        this.argsList = argsList;
        this.expectedArgsCount = expectedArgsCount;
        final int argc = argsList.size();

        if (this.expectedArgsCount != FunctionCall.ANY_ARGS_COUNT) {
            if (this.expectedArgsCount == FunctionCall.AT_LEAST_ONE_ARG) {
                if (argc < 1) {
                    throw new IllegalArgumentException(this.getName()
                            + " expects at least one argument, " + argc + " given instead.");
                }
            } else if (this.expectedArgsCount != argc) {
                throw new IllegalArgumentException(this.getName() + " expects "
                        + this.expectedArgsCount + " argument(s), " + argc + " given instead.");
            }
        }
    }

    public abstract String getName();
}
