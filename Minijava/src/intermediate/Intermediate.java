package intermediate;

import intermediate.ir.IRQuadruple;
import intermediate.ir.IRVar;
import intermediate.ir.QAssign;
import intermediate.ir.QAssignArrayFrom;
import intermediate.ir.QAssignUnary;
import intermediate.ir.QCall;
import intermediate.ir.QLabel;
import intermediate.ir.QLabelMeth;
import intermediate.ir.QLength;
import intermediate.ir.QParam;
import intermediate.ir.QReturn;
import main.DEBUG;
import semantic.SemanticAttribut;
import semantic.SemanticTree;
import syntax.ast.ASTNode;
import syntax.ast.ASTVisitorDefault;
import syntax.ast.ExprArrayLength;
import syntax.ast.ExprArrayLookup;
import syntax.ast.ExprCall;
import syntax.ast.ExprIdent;
import syntax.ast.ExprLiteralBool;
import syntax.ast.ExprLiteralInt;
import syntax.ast.ExprOpBin;
import syntax.ast.ExprOpUn;
import syntax.ast.KlassMain;
import syntax.ast.Method;
import syntax.ast.StmtPrint;


/** Générarion de la forme intermédiaire (Code à 3 adresses). */
public class Intermediate extends ASTVisitorDefault {
	/**
	 * Input : AST Décoré et Table de symbol AST.
	 */
	private final SemanticTree semanticTree;
	private final IR ir;
	/**
	 * Attribut synthétisé nodeVar = Variable IR Temp pour résultat des expression.
	 */
	private final SemanticAttribut<IRVar> varAttr;
	/**
	 * Attribut synthétisé utilisé comme portée pour les variables IRtemp.
	 */
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
	
	@Override
	public void visit(ExprLiteralInt exprLiteralInt) {
		this.setVar(exprLiteralInt, this.newConst(exprLiteralInt.value.intValue()));
	}
	
	@Override
	public void visit(ExprLiteralBool exprLiteralBool) {
		this.setVar(exprLiteralBool, this.newConst(exprLiteralBool.value.booleanValue() ? 1 : 0));
	}
	
	@Override
	public void visit(ExprIdent exprIdent) {
		this.setVar(exprIdent, this.lookupVar(exprIdent.varId.name, exprIdent));
	}
	
	@Override
	public void visit(ExprOpUn exprOpUn) {
		exprOpUn.expr.accept(this);
		this.setVar(exprOpUn, this.newTemp());
		this.add(new QAssignUnary(exprOpUn.op, this.getVar(exprOpUn.expr), this.getVar(exprOpUn)));
	}
	
	@Override
	public void visit(ExprOpBin exprOpBin) {
		exprOpBin.expr1.accept(this);
		exprOpBin.expr2.accept(this);
		
		this.setVar(exprOpBin, this.newTemp());
		this.add(new QAssign(exprOpBin.op, this.getVar(exprOpBin.expr1),
				this.getVar(exprOpBin.expr2), this.getVar(exprOpBin)));
	}
	
	@Override
	public void visit(ExprArrayLength exprArrayLength) {
		exprArrayLength.array.accept(this);
		this.setVar(exprArrayLength, this.newTemp());
		this.add(new QLength(this.getVar(exprArrayLength.array), this.getVar(exprArrayLength)));
	}
	
	@Override
	public void visit(ExprArrayLookup exprArrayLookup) {
		exprArrayLookup.array.accept(this);
		exprArrayLookup.index.accept(this);
		
		this.setVar(exprArrayLookup, this.newTemp());
		this.add(new QAssignArrayFrom(this.getVar(exprArrayLookup.array),
										this.getVar(exprArrayLookup.index),
										this.getVar(exprArrayLookup)));
	}
	
	@Override
	public void visit(KlassMain klassMain) {
		this.currentMethod = "main";
		this.add(new QLabel(this.newLabel(this.currentMethod)));
		this.defaultVisit(klassMain);
		this.add(new QCall(this.newLabel("_system_exit"), this.newConst(0), null));
		this.currentMethod = null;
	}
	
	@Override
	public void visit(Method method) {
		this.currentMethod = method.methodId.name;
		this.add(new QLabelMeth(this.newLabel(this.currentMethod)));
		
		method.vars.accept(this);
		method.stmts.accept(this);
		method.returnExp.accept(this);
		
		this.add(new QReturn(this.getVar(method.returnExp)));
		this.currentMethod = null;
	}
	
	@Override
	public void visit(ExprCall exprCall) {
		exprCall.receiver.accept(this);
		this.add(new QParam(this.getVar(exprCall.receiver)));
		
		for (ASTNode arg: exprCall.args) {
			arg.accept(this);
			this.add(new QParam(this.getVar(arg)));
		}
		
		this.setVar(exprCall, this.newTemp());
		this.add(new QCall(this.newLabel(exprCall.methodId.name),
							this.newConst(exprCall.args.size()),
							this.getVar(exprCall)));
	}
	
	@Override
	public void visit(StmtPrint stmtPrint) {
		this.defaultVisit(stmtPrint);
		this.add(new QParam(this.getVar(stmtPrint.expr)));
		this.add(new QCall(this.newLabel("_system_out_println"), this.newConst(1), null));
	}
	
	
	/** Stucture de donnée en sortie de la génération de code intermédiaire */
	public IR getResult() {
		return this.ir;
	}
	
	private IRVar getVar(ASTNode node) {
		return this.varAttr.get(node);
	}
	
	private IRVar setVar(ASTNode node, IRVar var) {
		return this.varAttr.set(node, var);
	}
	
	/**
	 * Ajouter une instruction au programmeIR.
	 */
	private void add(IRQuadruple irq) {
		this.ir.program.add(irq);
	}
	
	/**
	 * Variables IR : label tempo, label nom, Constante, Temp var.
	 */
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
	
	/**
	 * Variable de l'AST depuis la table de symbole.
	 */
	private IRVar lookupVar(String name, ASTNode node) {
		return this.semanticTree.scopeAttr.get(node).lookupVariable(name);
	}
}
