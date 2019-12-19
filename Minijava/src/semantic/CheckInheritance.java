package semantic;

import java.util.HashSet;

import main.DEBUG;
import semantic.symtab.InfoKlass;
import semantic.symtab.Scope;


/**
 * Validation de la hiérarchie des classes (parent connu, sans boucle, "Object"
 * comme racine).
 * <p>
 * Reconstruction de l'arbre des portées pour integration transparente de
 * l'héritage de classe dans la table des symboles
 */
public class CheckInheritance {
	/** Sortie en erreur */
	public boolean getError() {
		return this.error;
	}
	
	private boolean error;
	
	// Arbre des portées (input/output)
	private final Scope rootScope;
	
	// Classe Object : Requise comme racine de l'héritage Java
	private final InfoKlass objKlass;
	
	// Constructor
	public CheckInheritance(SemanticTree semanticTree) {
		this.error = false;
		this.rootScope = semanticTree.rootScope;
		this.objKlass = this.rootScope.lookupKlass("Object");
		this.checkAndBuild();
		
		if (this.error) {
			DEBUG.logErr("Error(s) in CheckInheritance : Java inheritance unmanaged for some classes");
		}
	}
	
	// ...
	private InfoKlass parent(InfoKlass kl) { // heritage sur InfoKlass
		return this.rootScope.lookupKlass(kl.getParent());
	}
	
	private boolean assume(boolean condition, String message) {
		// gestion erreur avec continuation
		if (!condition) {
			DEBUG.logErr(message);
			this.error = true;
		}
		
		return condition;
	}
	
	private void checkAndBuild() {
		// Classe "Object" valide ?
		if (!this.checkObject()) {
			return;
		}
		
		for (InfoKlass kl: this.rootScope.getKlasses()) {
			// reconstruit toutes les classes avec branche d'ancêtres valides
			if (this.checkAncestors(kl)) {
				for (InfoKlass k = kl; k != this.objKlass; k = this.parent(k)) {
					k.getScope().mute(this.rootScope, this.parent(k).getScope());
				}
			}
		}
	}
	
	private boolean checkObject() {
		// Object class exists as root
		return this.assume(this.objKlass != null, "Missing Object Class")
				&& this.assume(this.objKlass.getParent() == null, "Object Class extends !! " + this.objKlass);
	}
	
	private boolean checkAncestors(InfoKlass kl) {
		// branche sans boucles, de classes connues, et "Object" comme racine
		HashSet<InfoKlass> ancestors = new HashSet<>();
		boolean ok = true;
		
		for (InfoKlass k = kl; ok && k != this.objKlass; k = this.parent(k)) {
			ok = this.assume(ancestors.add(k), "Loop in ancestors from class " + kl)
					&& this.assume(this.parent(k) != null, "Unknown ancestor for " + k);
		}
		
		return ok;
	}
}
