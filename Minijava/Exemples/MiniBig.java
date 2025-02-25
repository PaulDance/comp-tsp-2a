/**
 * Implementation simpliste de BigNum en minijava Test :
 * 80435758145817515^3+12602123297335631^3-80538738812075974^3 formule
 * decouverte en Sept 2019 apres plus de 1 million d'heures de calcul Andrew
 * Sutherland (MIT) and Andrew Booker (Bristol cf
 * :https://en.wikipedia.org/wiki/Sums_of_three_cubes
 */
class MiniBig {
	public static void main(String[] args) {
		System.out.println(new ForTwo().go());
	}
}


class ForTwo {
	public int go() {
		BigNum a;
		BigNum b;
		BigNum c;
		BigNum d;
		a = new BigNum().pre(515).pre(817).pre(145).pre(758).pre(435).pre(80);
		b = new BigNum().pre(631).pre(335).pre(297).pre(123).pre(602).pre(12);
		c = new BigNum().pre(974).pre(75).pre(812).pre(738).pre(538).pre(80);
		d = a.mult(a).mult(a).add(b.mult(b).mult(b)).sub(c.mult(c).mult(c)).print();
		return 0;
	}
}


class BigNum {
	int size;
	int[] number;
	
	public int size() {
		return size;
	}
	
	public int[] number() {
		return number;
	}
	
	public BigNum pre(int digit) { // prepend : this += digit*1000^size
		if (size < 1)
			number = new int[24];
		else {
		}
		number[size] = digit;
		size = size + 1;
		return this;
	}
	
	public BigNum reduce() {// suppress zeros
		while ((1 < size) && (number[size - 1] < 1))
			size = size - 1;
		return this;
	}
	
	public BigNum print() {
		System.out.println(888888);
		int i;
		i = 0;
		while (i < size) {
			i = i + 1;
			System.out.println(number[size - i]);
		}
		System.out.println(888888);
		return this;
	}
	
	int retenu;
	
	public int euclide(int i) { // division euclidienne tres naive
		retenu = 0;
		while (999 < i) {
			i = i - 1000;
			retenu = retenu + 1;
		}
		while (i < 0) {
			i = i + 1000;
			retenu = retenu - 1;
		}
		return i;
	}
	
	public BigNum add(BigNum bn) {
		BigNum retour;
		retour = new BigNum();
		int i;
		i = 0;
		retenu = 0;
		int[] bnum;
		bnum = bn.number();
		int maxsize;
		maxsize = bn.size();
		if (maxsize < size)
			maxsize = size;
		else {
		}
		while (i < maxsize) {
			retour = retour.pre(this.euclide(number[i] + bnum[i] + retenu));
			i = i + 1;
		}
		return retour.pre(retenu).reduce();
	}
	
	public BigNum sub(BigNum bn) {
		BigNum retour;
		retour = new BigNum();
		int i;
		i = 0;
		retenu = 0;
		int[] bnum;
		bnum = bn.number();
		while (i < size) {
			retour = retour.pre(this.euclide(number[i] - bnum[i] + retenu));
			i = i + 1;
		}
		return retour.reduce();
	}
	
	public BigNum mult(BigNum bn) {
		BigNum retour;
		retour = new BigNum().pre(0);
		BigNum temp;
		int i;
		i = 0;
		int j;
		int[] bnum;
		bnum = bn.number();
		while (i < bn.size()) {
			j = 0;
			retenu = 0;
			temp = new BigNum();
			while (j < i) {
				temp = temp.pre(0);
				j = j + 1;
			}
			j = 0;
			while (j < size) {
				temp = temp.pre(this.euclide(number[j] * bnum[i] + retenu));
				j = j + 1;
			}
			retour = retour.add(temp.pre(retenu));
			i = i + 1;
		}
		return retour;
	}
}
