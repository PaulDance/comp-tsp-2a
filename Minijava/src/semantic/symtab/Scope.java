package semantic.symtab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import main.IndentWriter;


/**
 * Table des Symbole.
 * <ul>
 * <li>Implémentation plus large que nécessaire pour Java ou MiniJava
 * <li>Implémentation générale d'un arbre de portées avec lookup récursif
 * <li>3 espaces de noms séparés : Classe, Méthode, Variable
 * <li>Utilisable pour intégrer l'héritage des classes Java dans l'arbre de
 * portée
 * <li>.....
 * </ul>
 */
public class Scope {
    // Arbre Botom-up
    private Scope parent;
    // Tables de Symboles locales
    private final Table<String, InfoVar> variables;
    private final Table<String, InfoMethod> methods;
    private final Table<String, InfoKlass> klasses;
    /*
     * En pratique avec Minijava : - klasses uniquement dans le scope Racine -
     * méthodes uniquement dans les scopes de classe - variables partout sauf
     * dans le scope Racine
     */

    // Debug and Printing
    private final String name;
    private final List<Scope> scopes; // Arbre top-down

    // Constructors
    public Scope(final Scope parent) { // default naming
        this(parent, parent == null ? "Root" : parent.name + "_" + parent.scopes.size());
    }

    public Scope(final Scope parent, final String name) {
        this.name = name;
        this.parent = parent;
        this.variables = new SimpleTable<InfoVar>();
        this.methods = new SimpleTable<InfoMethod>();
        this.klasses = new SimpleTable<InfoKlass>();
        this.scopes = new ArrayList<Scope>();

        if (parent != null) {
            parent.scopes.add(this);
        }
    }

    /* Symtab Interface */
    // Variables
    public InfoVar lookupVariable(final String name) {
        InfoVar v = null;

        for (Scope s = this; s != null && v == null; s = s.parent) {
            v = s.variables.lookup(name);
        }

        return v;
    }

    public InfoVar insertVariable(final InfoVar v) {
        return this.variables.insert(v.getName(), v);
    }

    public Collection<InfoVar> getVariables() {
        return this.variables.getInfos();
    }

    /**
     * Variables de la portée courante et des sous-protées. Utile pour debug,
     * check unused, utilisation de variables de bloc.
     */
    public Collection<InfoVar> getAllVariables() {
        final List<InfoVar> res = new ArrayList<InfoVar>();
        res.addAll(this.variables.getInfos());

        for (final Scope s: this.scopes) {
            res.addAll(s.getAllVariables());
        }

        return res;
    }

    // Methods
    public InfoMethod lookupMethod(final String name) {
        InfoMethod m = null;

        for (Scope s = this; s != null && m == null; s = s.parent) {
            m = s.methods.lookup(name);
        }

        return m;
    }

    public InfoMethod insertMethod(final InfoMethod m) {
        return this.methods.insert(m.getName(), m);
    }

    public Collection<InfoMethod> getMethods() {
        return this.methods.getInfos();
    }

    // Not Used : requis pour gestion du polymorphisme/surcharge
    // utile aussi pour indication overriding
    public List<InfoMethod> lookupAllMethod(final String name) {
        final List<InfoMethod> list = new ArrayList<InfoMethod>();

        for (Scope s = this; s != null; s = s.parent) {
            final InfoMethod m = s.methods.lookup(name);

            if (m != null) {
                list.add(m);
            }
        }

        return list;
    }

    // Klasses
    public InfoKlass lookupKlass(final String name) {
        InfoKlass kl = null;

        for (Scope s = this; s != null && kl == null; s = s.parent) {
            kl = s.klasses.lookup(name);
        }

        return kl;
    }

    public InfoKlass insertKlass(final InfoKlass kl) {
        return this.klasses.insert(kl.getName(), kl);
    }

    public Collection<InfoKlass> getKlasses() {
        return this.klasses.getInfos();
    }

    /**
     * Reconstruction en Passe2 : intégration de la hierarchie des classes dans
     * l'arbre des portées.
     * <ul>
     * <li>Seul les fils de la racine peuvent muter dans un nouvelle arbre.
     * <li>Not safe : loop not checked here, field parent not final
     * <li>retourne true, si nodification effectuée
     * </ul>
     */
    public boolean mute(final Scope oldParent, final Scope newParent) {
        // mute only a level 1 scope (parent is a rootScope)
        if (this.parent == null || this.parent.parent != null) {
            return false;
        }
        // don't create a new root
        if (newParent == null) {
            return false;
        }
        // mute only a known parent
        if (this.parent != oldParent) {
            return false;
        }
        // OK
        this.parent = newParent;
        this.parent.scopes.add(this);
        oldParent.scopes.remove(this);
        return true;
    }

    // Impressions
    /** Impression Noeud */
    @Override
    public String toString() {
        return "Scope " + this.name;
    }

    /** Impression Arbre bottom-up */
    public String toPrintUp() {
        return this.name + " -> " + this.parent.toPrintUp();
    }

    /** Impression Arbre top-down avec symboles */
    public String toPrint() {
        final IndentWriter out = new IndentWriter();
        this.print(out);
        return out.toString();
    }

    private void print(final IndentWriter out) {
        out.println("Scope " + this.name);
        out.indent();

        for (final Info i: this.getKlasses()) {
            out.println(i);
        }

        for (final Info i: this.getMethods()) {
            out.println(i);
        }

        for (final Info i: this.getVariables()) {
            out.println(i);
        }

        for (final Scope s: this.scopes) {
            s.print(out);
        }

        out.outdent();
    }
}
