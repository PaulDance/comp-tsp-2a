package intermediate;

import intermediate.ir.IRQuadruple;
import intermediate.ir.IRVar;
import intermediate.ir.QCall;
import intermediate.ir.QLabel;
import intermediate.ir.QParam;
import main.DEBUG;
import semantic.SemanticAttribut;
import semantic.SemanticTree;
import syntax.ast.ASTNode;
import syntax.ast.ASTVisitorDefault;
import syntax.ast.ExprLiteralInt;
import syntax.ast.KlassMain;
import syntax.ast.StmtPrint;


/** Générarion de la forme intermédiaire (Code à 3 adresses). */
public class Intermediate extends ASTVisitorDefault {
	// Input : AST Décoré et Table de symbol AST
	private final SemanticTree semanticTree;
	
	/** Stucture de donnée en sortie de la génératrion de code intermédiaire */
	public IR getResult() {
		return this.ir;
	}
	
	private final IR ir;
	
	// Attribut synthetisee nodeVar = Variable IR Temp pour resultat des
	// expression
	private final SemanticAttribut<IRVar> varAttr; // nom Variables dans IR
	
	private IRVar getVar(ASTNode n) {
		return this.varAttr.get(n);
	}
	
	private IRVar setVar(ASTNode n, IRVar var) {
		return this.varAttr.set(n, var);
	}
	
	// Attribut synthétisé utilisé comme portée pour les variables IRtemp
	private String currentMethod;
	
	/** Constructeur */
	public Intermediate(SemanticTree semanticTree) {
		this.semanticTree = semanticTree;
		this.ir = new IR(semanticTree);
		this.varAttr = new SemanticAttribut<>();
		this.currentMethod = null;
		semanticTree.axiom.accept(this); // => visite((Raxiome)axiome)
		
		if (DEBUG.INTERMED) {
			DEBUG.log(this.ir);
		}
	}
	
	//// Helpers
	// Ajouter une instruction au programmeIR
	private void add(IRQuadruple irq) {
		this.ir.program.add(irq);
	}
	
	// Variables IR : label tempo, label nom, Constante, Temp var
	private IRVar newLabel() {
		return this.ir.newLabel();
	}
	
	private IRVar newLabel(String name) {
		return this.ir.newLabel(name);
	}
	
	private IRVar newConst(int value) {
		return this.ir.newConst(value);
	}
	
	private IRVar newTemp() {
		return this.ir.newTemp(this.currentMethod);
	}
	
	// Variable de l'AST depuis la table de symbole
	private IRVar lookupVar(String name, ASTNode n) {
		return this.semanticTree.scopeAttr.get(n).lookupVariable(name);
	}
	
	/////////////////// Visit ////////////////////
	@Override
	public void visit(KlassMain n) {
		this.currentMethod = "main";
		this.add(new QLabel(this.newLabel(this.currentMethod)));
		this.defaultVisit(n);
		this.add(new QCall(this.newLabel("_system_exit"), this.newConst(0), null));
		this.currentMethod = null;
	}
	
	@Override
	public void visit(ExprLiteralInt n) {
		this.setVar(n, this.newConst(n.value));
	}
	
	@Override
	public void visit(StmtPrint n) {
		this.defaultVisit(n);
		this.add(new QParam(this.getVar(n.expr)));
		this.add(new QCall(this.newLabel("_system_out_println"), this.newConst(1), null));
	}
	
}
