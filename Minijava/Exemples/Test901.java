class Test901 {
	public static void main(String[] args) {
		System.out.println(new Test().start());
	}
}


class Test2 extends Test {
	public Test2 start() {
		test = this;
		return this;
	}
	
	public boolean next() {
		test = test.also(test);
		test = test2.also(test.next());
		return true;
	}
}


class Test {
	Test test;
	Test2 test2;
	boolean b;
	
	public int start() {
		test = this.next();
		b = test2.next();
		test = (test.next()).next();
		test = (new Test()).next();
		test = ((new Test2()).also(test)).also(this);
		return 42;
	}
	
	public Test next() {
		return test2.start();
	}
	
	public Test also(Test test1) {
		return test2.start();
	}
}
