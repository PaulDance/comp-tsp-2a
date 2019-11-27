package semantic;

import java.util.HashSet;
import main.DEBUG;
import semantic.symtab.InfoKlass;
import semantic.symtab.Scope;

/** Validation de la hierarchie des classes (parent connu, sans boucle, "Object" comme racine).
 * <p> Reconstruction de l'arbre des portées pour integration transparente 
 * de l'héritage de classe dans la table des symboles
 */
public class CheckInheritance {
  /** Sortie en erreur */
  public boolean getError() {  return error; }
  private boolean error;
 
  // Arbre des portées (input/output)
  private final Scope rootScope;
  
  // Classe Object : Requise comme racine de l'héritage Java
  private final InfoKlass objKlass;

   // Constructor
  public CheckInheritance(SemanticTree semanticTree) {
    this.error = false;
    this.rootScope = semanticTree.rootScope;
    this.objKlass = rootScope.lookupKlass("Object");
    checkAndBuild();
    if (error)
      DEBUG.logErr("Error(s) in CheckInheritance : Java inheritance unmanaged for some classes");
  }
  
  // ...
  private InfoKlass parent(InfoKlass kl) { // heritage sur InfoKlass
    return rootScope.lookupKlass(kl.getParent());
  }
  
  private boolean assume(boolean condition, String message) {
    // gestion erreur avec continuation
    if (!condition) {
      DEBUG.logErr(message);
      error = true;
    }
    return condition;
  }

  private void checkAndBuild() { 
    // Classe "Object" valide ?
    if ( ! checkObject() ) return; 
    for (InfoKlass kl : rootScope.getKlasses())
      // reconstruit toutes les classes avec branche d'ancêtres valides
     if (checkAncestors(kl))
        for (InfoKlass k = kl; (k != objKlass); k = parent(k))
          k.getScope().mute(rootScope, parent(k).getScope());
  }
  
  private boolean checkObject(){
    //  Object class exists as root
    return assume(objKlass != null, "Missing Object Class")
           &&
           assume(objKlass.getParent()==null, "Object Class extends !! " +objKlass);
  }
  
  private boolean checkAncestors (InfoKlass kl) {
    // branche sans boucles, de classes connues, et "Object" comme racine
    HashSet<InfoKlass> ancestors = new HashSet<>();
    boolean ok = true;
    for (InfoKlass k = kl; ok && (k != objKlass); k = parent(k))
      ok = assume(ancestors.add(k), "Loop in ancestors from class " + kl)
      && assume(parent(k) != null, "Unknown ancestor for " + k);
    return ok;
  }
}
