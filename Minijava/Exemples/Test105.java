class Test105 {
	public static void main(String[] args) {
		System.out.println(new Test2().Start(0)); // -42 !!
	}
}


class Test2 {
	public int Start(int y) {
		int a;
		int b;
		
		boolean b1;
		boolean b2;
		
		a = 3;
		a = a + a;
		a = a + 1;
		a = 1 + a;
		a = a - 1 - 1;
		a = 1 - a;
		a = a * a;
		a = -1 * a;
		b = y;
		b = 3 * 3;
		b = this.evil();
		
		b1 = true;
		b2 = false;
		b1 = b1 && b2 && !b1;
		
		return 2 * a + 8;
	}
	
	public int evil() {
		return 666;
	}
}
