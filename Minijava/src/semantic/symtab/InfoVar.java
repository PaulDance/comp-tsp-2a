package semantic.symtab;

/**
 * Déclaration de Varaible pour la table de symboles. Suivant le scope une
 * Variable est :
 * <ul>
 * <li>un champs de classe
 * <li>un argument de methode
 * <li>une variable locale de methode
 * <li>une variable locale de bloc
 * </ul>
 */
public class InfoVar implements Info, intermediate.ir.IRVar {
	private final String name;
	private final String type;
	
	public InfoVar(String name, String type) {
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getType() {
		return this.type;
	}
	
	@Override
	public String toString() {
		return this.type + " " + this.name;
	}
}
