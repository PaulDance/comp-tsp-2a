class Test203 {
	public static void main(String[] a) {
		System.out.println(new Operator().compute()); // =42
	}
}


class Operator {
	boolean b; // initialized to false ?
	int i; // initialized to 0 ?
	
	public int compute() {
		int j;
		b = this.next();
		i = 10;
		
		if (b) {
			j = 10; // => 42
		}
		else {
			j = 0; // => -8
		}
		
		return i + (j * 5 - 8) - 10;
	}
	
	public boolean next() {
		return true && (7 < 8) && (i < 5) && !b;
	}
}
