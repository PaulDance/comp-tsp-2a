package semantic.symtab;

import java.util.Collection;
import java.util.List;


/**
 * Déclaration de Methode pour la table de symboles.
 * <p>
 * Insertion d'une Methode :
 * <ul>
 * <li>Ajout de la Methode : scope.add(new InfoMethod);
 * <li>Entrée dans un nouveau scope : scope=new Scope(scope);
 * <li>Ajout des arguments Formels: scope.add(new InfoVar); ...
 * <li>Entrée dans un nouveau scope : scope=new Scope(scope);
 * <li>mise a jour : InfoMethod.setScope(scope);
 * </ul>
 * <p>
 * Le scope de la Méthode est fils du scope des Arguments qui est file du scope
 * de déclaration de methode.
 * <p>
 * NB : les arguments formels sont dupliqués comme une liste ordonée dans
 * InfoMethod et comme une collection non ordonée dans le scope des argument
 */
public class InfoMethod implements Info {
    private final String returnType;
    private final String name;
    private final InfoVar[] args;
    // NB: currentKlassName == Type de args[0] = "this"

    /** link to Method attributes : formal args , local variables */
    private Scope scope;

    // getters
    public String getReturnType() {
        return this.returnType;
    }

    public String getName() {
        return this.name;
    }

    public InfoVar[] getArgs() {
        return this.args;
    }

    public Scope getScope() {
        return this.scope;
    }

    // setters
    public void setScope(final Scope sc) {
        this.scope = sc;
    }

    // helpers
    public Collection<InfoVar> getLocals() {
        return this.scope.getVariables();
    }
    // not used : getArgs=scope.getParent().getVariables)_
    // TO DO ? getAllLocals (recursif top-down) pour gestion des variables de
    // block

    // constructors : varargs or List<>
    public InfoMethod(final String returnType, final String name, final InfoVar... args) {
        this.returnType = returnType;
        this.name = name;
        this.args = args;
        this.scope = null;
    }

    public InfoMethod(final String returnType, final String name, final List<InfoVar> args) {
        this.returnType = returnType;
        this.name = name;
        this.args = args.toArray(new InfoVar[0]);
        this.scope = null;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.returnType).append(' ').append(this.name);
        sb.append('(');

        if (this.args.length != 0) {
            sb.append(this.args[0]);
        }

        for (int i = 1; i < this.args.length; i++) {
            sb.append(", ").append(this.args[i]);
        }

        sb.append(')');
        return sb.toString();
    }

    // Not Used : Nom Unique pour polymorphisme/surcharge/redefinition
    public String getCanonicalName() {
        final StringBuilder sb = new StringBuilder();
        sb.append("__").append(this.name);

        for (final InfoVar v: this.args) {
            sb.append('_').append(v.getType());
        }

        return sb.toString();
    }
}
