class Test202 {
	public static void main(String[] a) {
		System.out.println(new Operator().compute()); // => 42
	}
}


class Operator {
	boolean b1;
	boolean b2;
	boolean b;
	int i1;
	int i2;
	int i;
	
	public int compute() {
		b1 = true;
		b2 = false;
		i1 = 6;
		i2 = 7;
		b = !(!b2 && (i1 < i2));
		
		if (b)
			i = i1 + i2;
		else
			i = i1 + i1 * i2 - i2 + 1;
		
		return i;
	}
}
