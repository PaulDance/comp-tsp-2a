package semantic.symtab;

import java.util.Collection;


/**
 * Interface standard d'une table de Symbole.
 * <ul>
 * <li>T = name index (String)
 * <li>R = content information
 * </ul>
 */
public interface Table<T, R extends Info> {
    /** Recherche dans la table */
    R lookup(T name);

    /** Ajout dans la table. Retour != null , si name existe déjà */
    R insert(T name, R info);

    /** Collection des symboles de la table */
    Collection<R> getInfos();
}
// Table avec Scope :
// lookupLocal() : may be not usefull if insert return "already exists"
// enterScope() -> currentScope = new Scope (currentScope)
// exitScope() -> currentScope = curentScope.getParent()
// ou currentScope = savedCurrentScope
