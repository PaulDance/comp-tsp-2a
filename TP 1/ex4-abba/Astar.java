public class Astar {
	static final int TAILLE_MAX = 255;
	static final String USAGE=
		"Syntaxe :\n" +
		"> java Astar str N     # mots de taille = N \n" +
		"> java Astar str -N    # mots de taille <= N \n" +
		"     et str = alphabet = string de char (Java)\n" +
		"Exemples:\n" +
		"> java Astar abc -9 => les mots sur {a,b,c} de taille 0 a 9\n" +
		"> java Astar 01 7   => les nombres binaires sur 7 bits\n" ;
	
	static String alphabet;		// args[0]
	static int arg2;			// args[1] (= taille ou -taille_max)
	static char[] result;		// var globale pour recurse() 
	static int taille;			// var globale pour recurse()
	
	static private void recurse(int level) {
		if (level == 0) {
			result = new char[taille];
		}
		
		if (level == taille) {
			System.out.println(result);
		}
		else {
			for (int i = 0; i < alphabet.length(); i++) {
				result[level] = alphabet.charAt(i);
				recurse(level + 1);
			}
		}
	}
	
	public static void main(String[] args){
		if (args.length != 2) {
			System.out.println(USAGE);
			return;
		}
		
		alphabet = args[0];
		arg2 = Integer.parseInt(args[1]);
		
		if (Math.abs(arg2) > TAILLE_MAX) {
			System.out.println("Taille trop grande");
			return;
		} 
		
		if (arg2 >= 0) {
			taille = arg2;
			recurse(0);
		}
		else {
			for (taille = 0; taille <= -arg2; taille++) {
				recurse(0);
			}
		}
	}
}

