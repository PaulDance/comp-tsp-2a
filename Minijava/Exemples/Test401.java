class Test401 {
	public static void main(String[] a) {
		System.out.println(new Operator().compute());
	}
}


class Op2 {
}


class Operator {
	boolean b;
	int i;
	Operator op;
	Op2 op2;
	
	public int compute() {
		i = op; // FAIL : type
		b = i; // FAIL : type
		i = true && false; // FAIL : type
		i = 6 < 7; // FAIL : type
		i = !false; // FAIL : type
		b = 6 + 7; // FAIL : type
		b = 6 - 7; // FAIL : type
		b = 6 * 7; // FAIL : type
		
		b = true && 7; // FAIL : type
		b = true < 7; // FAIL : type
		b = !6; // FAIL : type
		i = true + 7; // FAIL : type
		i = 6 - false; // FAIL : type
		i = true * false; // FAIL : type
		op = op2; // FAIL : type
		
		System.out.println(true); // FAIL : type
		System.out.println(op); // FAIL : type
		System.out.println(op.get()); // FAIL : type
		System.out.println(op.compute()); // OK
		
		while (i) {
		} // FAIL : type
		while (op) {
		} // FAIL : type
		while (op.compute()) {
		} // FAIL : type
		if (i) {
		}
		else {
		} // FAIL : type
		
		return b; // FAIL : type ??
	}
	
	public Operator get() {
		return true; // FAIL return Type
	}
}
