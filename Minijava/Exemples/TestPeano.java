/*
 * Entier de Peano et poupée russe requièrent UTF8 en MIPS (MARS OK).
 */
class TestPeano {
	public static void main(String[] args) {
		System.out.println(new Peano().test()); // print 0
	}
}


class Матрёшка { // Matriochka == entier de Peano
	Матрёшка кукла;
	
	public Матрёшка set(Матрёшка doll) {
		кукла = doll;
		return this;
	}
	
	public Матрёшка get() {
		return кукла;
	}
}


class Peano {
	Матрёшка НОЛЬ; // constante null
	
	public int test() {
		НОЛЬ = new Матрёшка();
		Матрёшка d1;
		d1 = this.init(5);
		Матрёшка d2;
		d2 = this.init(3);
		return this.печатать(d1) + this.печатать(d2) - this.печатать(this.плюс(d1, d2));
	}
	
	public Матрёшка init(int level) { // return Matriochka(Matriochka(...(null)...))
		Матрёшка doll;
		
		if (level < 1) {
			doll = НОЛЬ;
		}
		else {
			doll = new Матрёшка().set(this.init(level - 1));
		}
		
		return doll;
	}
	
	public Матрёшка плюс(Матрёшка d1, Матрёшка d2) { // addition de Peano
		Матрёшка doll;
		
		if (d1.equals(НОЛЬ)) {
			doll = d2;
		}
		else {
			doll = this.плюс(d1.get(), new Матрёшка().set(d2));
		}
		
		return doll;
	}
	
	public int печатать(Матрёшка doll) { // impression
		int i;
		
		if (doll.equals(НОЛЬ)) {
			i = 0;
		}
		else {
			i = 1 + this.печатать(doll.get());
		}
		
		System.out.println(i);
		return i;
	}
	
}
