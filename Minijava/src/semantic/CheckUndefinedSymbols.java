package semantic;

import semantic.symtab.Scope;
import syntax.ast.ASTVisitorDefault;
import syntax.ast.ExprIdent;
import syntax.ast.ExprNew;
import syntax.ast.Ident;
import syntax.ast.StmtArrayAssign;
import syntax.ast.StmtAssign;


public class CheckUndefinedSymbols extends ASTVisitorDefault {
	private final SemanticTree semanticTree;
	private boolean error;
	
	public CheckUndefinedSymbols(SemanticTree semanticTree) {
		this.error = false;
		this.semanticTree = semanticTree;
		this.semanticTree.axiom.accept(this);
	}
	
	@Override
	public void visit(StmtAssign stmtAssign) {
		this.checkIdent(stmtAssign.varId);
		stmtAssign.value.accept(this);
	}
	
	@Override
	public void visit(StmtArrayAssign stmtArrayAssign) {
		this.checkIdent(stmtArrayAssign.arrayId);
		stmtArrayAssign.index.accept(this);
		stmtArrayAssign.value.accept(this);
	}
	
	@Override
	public void visit(ExprIdent exprIdent) {
		this.checkIdent(exprIdent.varId);
	}
	
	@Override
	public void visit(ExprNew exprNew) {
		this.checkIdent(exprNew.klassId, true);
	}
	
	public void checkIdent(Ident ident) {
		this.checkIdent(ident, false);
	}
	
	public void checkIdent(Ident ident, boolean checkKlass) {
		final Scope identScope = this.semanticTree.scopeAttr.get(ident);
		
		if (checkKlass) {
			if (identScope.lookupKlass(ident.name) == null) {
				this.error = true;
				System.out.println(String.format("Error when reading identifier '%s': no such "
													+ "class exists in current scope '%s'.",
												ident.toString(),
												identScope.toString()));
			}
		}
		else if (identScope.lookupVariable(ident.name) == null) {
			this.error = true;
			System.out.println(String.format("Error when reading identifier '%s': no local "
												+ "method argument or variable or instance "
												+ "attribute exists in current scope '%s'.",
											ident.toString(),
											identScope.toString()));
		}
	}
	
	public boolean getError() {
		return this.error;
	}
}
