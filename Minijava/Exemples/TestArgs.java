class TestArgsMain {
	public static void main(String[] a) {
//		System.out.println(new TestArgs().test(1, 2, 3, 4, 5, 6, 7, 8, 9));
//		System.out.println(new TestArgs().test2());
		System.out.println(new TestArgs().f(4, 1, 2, 3, 4));	// 10
	}
}


class TestArgs {
	int x;
	
	public int f(int a, int b, int c, int d, int e) {
		int res;
		
		if (a < 1) {
			res = 0;
		}
		else {
			res = e + this.f(a - 1, e, b, c, d);
		}
		
		return res;
	}
	
	public int test(int i, int j, int k, int l, int m, int n, int o, int p, int q) {
		System.out.println(i);
		System.out.println(j);
		System.out.println(k);
		System.out.println(l);
		System.out.println(m);
		System.out.println(n);
		System.out.println(o);
		System.out.println(p);
		System.out.println(q);
		System.out.println(888888);
		
		if (0 < x) {
			int tmp;
			x = x - 1;
			tmp = this.test(q, i, j, j, j, m, n, o, p);
		}
		
		return 888888;
	}
	
	public int test2() {
		x = 2;
		x = this.test(1, 2, 3, 4, 5, 6, 7, 8, 9);
		return 0;
	}
}