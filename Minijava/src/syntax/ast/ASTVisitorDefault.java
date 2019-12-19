package syntax.ast;

/** Visiteur générique de l'AST avec parcours en profondeur. */
public class ASTVisitorDefault implements ASTVisitor {
	/** parcours récursif en profondeur. */
	public void defaultVisit(ASTNode node) {
		for (ASTNode f: node) {
			f.accept(this);
		}
	}
	
	// Liste homogéne, extends ASTNode
	@Override
	public <T extends ASTNode> void visit(ASTList<T> nodeList) {
		this.defaultVisit(nodeList);
	}
	
	// Productions de base, extends ASTNode
	@Override
	public void visit(Axiom axiom) {
		this.defaultVisit(axiom);
	}
	
	@Override
	public void visit(Klass klass) {
		this.defaultVisit(klass);
	}
	
	@Override
	public void visit(KlassMain klassMain) {
		this.defaultVisit(klassMain);
	}
	
	@Override
	public void visit(Method method) {
		this.defaultVisit(method);
	}
	
	@Override
	public void visit(Formal formal) {
		this.defaultVisit(formal);
	}
	
	@Override
	public void visit(Ident ident) {
		this.defaultVisit(ident);
	}
	
	@Override
	public void visit(Type type) {
		this.defaultVisit(type);
	}
	
	@Override
	public void visit(Var var) {
		this.defaultVisit(var);
	}
	
	// Expressions , extends Expr
	@Override
	public void visit(ExprArrayLength exprArrayLength) {
		this.defaultVisit(exprArrayLength);
	}
	
	@Override
	public void visit(ExprArrayLookup exprArrayLookup) {
		this.defaultVisit(exprArrayLookup);
	}
	
	@Override
	public void visit(ExprArrayNew exprArrayNew) {
		this.defaultVisit(exprArrayNew);
	}
	
	@Override
	public void visit(ExprCall exprCall) {
		this.defaultVisit(exprCall);
	}
	
	@Override
	public void visit(ExprIdent exprIdent) {
		this.defaultVisit(exprIdent);
	}
	
	@Override
	public void visit(ExprLiteralBool exprLiteralBool) {
		this.defaultVisit(exprLiteralBool);
	}
	
	@Override
	public void visit(ExprLiteralInt exprLiteralInt) {
		this.defaultVisit(exprLiteralInt);
	}
	
	@Override
	public void visit(ExprNew exprNew) {
		this.defaultVisit(exprNew);
	}
	
	@Override
	public void visit(ExprOpBin exprOpBin) {
		this.defaultVisit(exprOpBin);
	}
	
	@Override
	public void visit(ExprOpUn exprOpUn) {
		this.defaultVisit(exprOpUn);
	}
	
	// Instructions, extends Stmt
	@Override
	public void visit(StmtArrayAssign stmtArrayAssign) {
		this.defaultVisit(stmtArrayAssign);
	}
	
	@Override
	public void visit(StmtAssign stmtAssign) {
		this.defaultVisit(stmtAssign);
	}
	
	@Override
	public void visit(StmtBlock stmtBlock) {
		this.defaultVisit(stmtBlock);
	}
	
	@Override
	public void visit(StmtIf stmtIf) {
		this.defaultVisit(stmtIf);
	}
	
	@Override
	public void visit(StmtPrint stmtPrint) {
		this.defaultVisit(stmtPrint);
	}
	
	@Override
	public void visit(StmtWhile stmtWhile) {
		this.defaultVisit(stmtWhile);
	}
}
