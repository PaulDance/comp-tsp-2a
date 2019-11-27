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
	public <T extends ASTNode> void visit(ASTList<T> n) {
		this.defaultVisit(n);
	}
	
	// Productions de base, extends ASTNode
	@Override
	public void visit(Axiom n) {
		this.defaultVisit(n);
	}
	
	@Override
	public void visit(Klass n) {
		this.defaultVisit(n);
	}
	
	@Override
	public void visit(KlassMain n) {
		this.defaultVisit(n);
	}
	
	@Override
	public void visit(Method n) {
		this.defaultVisit(n);
	}
	
	@Override
	public void visit(Formal n) {
		this.defaultVisit(n);
	}
	
	@Override
	public void visit(Ident n) {
		this.defaultVisit(n);
	}
	
	@Override
	public void visit(Type n) {
		this.defaultVisit(n);
	}
	
	@Override
	public void visit(Var n) {
		this.defaultVisit(n);
	}
	
	// Expressions , extends Expr
	@Override
	public void visit(ExprArrayLength n) {
		this.defaultVisit(n);
	}
	
	@Override
	public void visit(ExprArrayLookup n) {
		this.defaultVisit(n);
	}
	
	@Override
	public void visit(ExprArrayNew n) {
		this.defaultVisit(n);
	}
	
	@Override
	public void visit(ExprCall n) {
		this.defaultVisit(n);
	}
	
	@Override
	public void visit(ExprIdent n) {
		this.defaultVisit(n);
	}
	
	@Override
	public void visit(ExprLiteralBool n) {
		this.defaultVisit(n);
	}
	
	@Override
	public void visit(ExprLiteralInt n) {
		this.defaultVisit(n);
	}
	
	@Override
	public void visit(ExprNew n) {
		this.defaultVisit(n);
	}
	
	@Override
	public void visit(ExprOpBin n) {
		this.defaultVisit(n);
	}
	
	@Override
	public void visit(ExprOpUn n) {
		this.defaultVisit(n);
	}
	
	// Instructions, extends Stmt
	@Override
	public void visit(StmtArrayAssign n) {
		this.defaultVisit(n);
	}
	
	@Override
	public void visit(StmtAssign n) {
		this.defaultVisit(n);
	}
	
	@Override
	public void visit(StmtBlock n) {
		this.defaultVisit(n);
	}
	
	@Override
	public void visit(StmtIf n) {
		this.defaultVisit(n);
	}
	
	@Override
	public void visit(StmtPrint n) {
		this.defaultVisit(n);
	}
	
	@Override
	public void visit(StmtWhile n) {
		this.defaultVisit(n);
	}
}
