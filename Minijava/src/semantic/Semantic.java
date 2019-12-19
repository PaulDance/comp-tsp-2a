package semantic;

import main.CompilerException;
import main.DEBUG;
import semantic.symtab.InfoVar;
import syntax.ast.ASTNode;


/** Analyse Sémantique */
public class Semantic {
	// Continuation sur erreur jusqu'a la fin de l'analyse semantique
	private boolean error;
	
	/** Stucture de donnée en sortie de l'analyse sémantique */
	public SemanticTree getResult() {
		return this.semanticTree;
	}
	
	private final SemanticTree semanticTree;
	
	/** Constructeur */
	public Semantic(ASTNode axiom) {
		this.semanticTree = new SemanticTree(axiom);
		this.error = false;
		this.analyse();
		
		if (this.error) {
			throw new CompilerException("Semantic Error(s)");
		}
	}
	
	private void analyse() {
		// Construction de la table de symbole (passe 1)
		// Controle la duplication de définition ("already defined")
		BuildSymTab bst = new BuildSymTab(this.semanticTree);
		this.error = bst.getError() || this.error;
		
		if (DEBUG.SYMTAB) {
			DEBUG.log("= Table des Symboles (passe1)");
			DEBUG.log(this.semanticTree.rootScope.toPrint());
		}
		
		// Construction de la hiérarchie des classes Java
		// - Contrôle consistance de l'héritage (loop, "Object",..)
		// - Integration de l'héritage dans la table des symboles (passe 2)
		CheckInheritance cch = new CheckInheritance(this.semanticTree);
		this.error = cch.getError() || this.error;
		
		if (DEBUG.SYMTAB) {
			DEBUG.log("= Table des Symboles (passe2)");
			DEBUG.log(this.semanticTree.rootScope.toPrint());
			DEBUG.log("= Liste des variables");
			DEBUG.log(this.semanticTree.rootScope.getAllVariables());
		}
		
		CheckUndefinedSymbols cUs = new CheckUndefinedSymbols(this.semanticTree);
		this.error = cUs.getError() || this.error;
		
		CheckUnusedSymbols cus = new CheckUnusedSymbols(this.semanticTree);
		this.error = cus.getError() || this.error;
		
		if (DEBUG.UNUSED) {
			for (InfoVar unusedVar: cus.unusedVars) {
				System.out.println(String.format("Warning: unused variable or attribute '%s'.", unusedVar));
			}
		}
		
		// Controle de Type et calcul de l'attribut nodeType
		TypeChecking tc = new TypeChecking(this.semanticTree);
		this.error = tc.getError() || this.error;
		
		// Controle des identificateurs non définis
		// Controle des declarations de variables unused dans la meme phase
		// NB : le controle de type est requis avant
		// ...
	}
}
