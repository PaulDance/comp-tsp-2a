package calculator;


public class Literal extends Expression {
    private final Double value;

    public Literal(final Double value) {
        this.value = value;
    }

    @Override
    public Double evaluate() {
        return this.value;
    }
}
