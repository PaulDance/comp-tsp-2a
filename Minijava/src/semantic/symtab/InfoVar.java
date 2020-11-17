package semantic.symtab;

/**
 * Déclaration de Variable pour la table de symboles. Suivant le scope une
 * Variable est :
 * <ul>
 * <li>un champs de classe
 * <li>un argument de méthode
 * <li>une variable locale de méthode
 * <li>une variable locale de bloc
 * </ul>
 */
public class InfoVar implements Info, intermediate.ir.IRVar {
    private final String name;
    private final String type;

    public InfoVar(final String name, final String type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return this.type + " " + this.name;
    }
}
