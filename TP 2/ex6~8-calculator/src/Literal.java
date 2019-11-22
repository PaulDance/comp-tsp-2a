
public class Literal extends Expression {
	private final Double value;
	
	public Literal(Double value) {
		this.value = value;
	}
	
	@Override
	public Double evaluate() {
		return this.value;
	}
}
