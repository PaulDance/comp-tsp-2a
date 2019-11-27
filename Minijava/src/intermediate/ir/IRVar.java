package intermediate.ir;

/**
 * Classe Commune pour la table des symboles de l'AST (InfoVar) et la table des
 * symboles de la forme intermédiaire (IRConst, IRTemp,IRLabel)
 */
public interface IRVar {
	/** Nom de la variable */
	public String getName();
	
	/** Tyoe de la variable */
	public String getType();
}
