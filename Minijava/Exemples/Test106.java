class Test106 {
	public static void main(String[] args) {
		System.out.println(new Test2().Start(0)); // Prints 42
		System.out.println(new Test2().Start(1)); // Prints 42
	}
}


class Test2 {
	public int Start(int n) {
		int a;
		int b;
		a = 0;
		b = n;
		
		if (!(a < b)) {
			a = 7;
		}
		else
			a = 2;
		
		while (a < 6) {
			a = a + 1;
		}
		
		return a * (a + 2 * b - 1);
	}
}
