package calculator.operations;

import calculator.Expression;


public class SubstractionOperation extends Expression {
    private final Expression op1, op2;

    public SubstractionOperation(final Expression op1, final Expression op2) {
        super(op1, op2);
        this.op1 = op1;
        this.op2 = op2;
    }

    @Override
    public Double evaluate() {
        return this.op1.evaluate() - this.op2.evaluate();
    }
}
