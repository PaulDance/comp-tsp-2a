package syntax.ast;

/** Visiteur générique de l'AST avec parcours en profondeur. */
public class ASTVisitorDefault implements ASTVisitor {
    /** parcours récursif en profondeur. */
    public void defaultVisit(final ASTNode node) {
        for (final ASTNode f: node) {
            f.accept(this);
        }
    }

    // Liste homogène, extends ASTNode
    @Override
    public <T extends ASTNode> void visit(final ASTList<T> nodeList) {
        this.defaultVisit(nodeList);
    }

    // Productions de base, extends ASTNode
    @Override
    public void visit(final Axiom axiom) {
        this.defaultVisit(axiom);
    }

    @Override
    public void visit(final Klass klass) {
        this.defaultVisit(klass);
    }

    @Override
    public void visit(final KlassMain klassMain) {
        this.defaultVisit(klassMain);
    }

    @Override
    public void visit(final Method method) {
        this.defaultVisit(method);
    }

    @Override
    public void visit(final Formal formal) {
        this.defaultVisit(formal);
    }

    @Override
    public void visit(final Ident ident) {
        this.defaultVisit(ident);
    }

    @Override
    public void visit(final Type type) {
        this.defaultVisit(type);
    }

    @Override
    public void visit(final Var var) {
        this.defaultVisit(var);
    }

    // Expressions , extends Expr
    @Override
    public void visit(final ExprArrayLength exprArrayLength) {
        this.defaultVisit(exprArrayLength);
    }

    @Override
    public void visit(final ExprArrayLookup exprArrayLookup) {
        this.defaultVisit(exprArrayLookup);
    }

    @Override
    public void visit(final ExprArrayNew exprArrayNew) {
        this.defaultVisit(exprArrayNew);
    }

    @Override
    public void visit(final ExprCall exprCall) {
        this.defaultVisit(exprCall);
    }

    @Override
    public void visit(final ExprIdent exprIdent) {
        this.defaultVisit(exprIdent);
    }

    @Override
    public void visit(final ExprLiteralBool exprLiteralBool) {
        this.defaultVisit(exprLiteralBool);
    }

    @Override
    public void visit(final ExprLiteralInt exprLiteralInt) {
        this.defaultVisit(exprLiteralInt);
    }

    @Override
    public void visit(final ExprNew exprNew) {
        this.defaultVisit(exprNew);
    }

    @Override
    public void visit(final ExprOpBin exprOpBin) {
        this.defaultVisit(exprOpBin);
    }

    @Override
    public void visit(final ExprOpUn exprOpUn) {
        this.defaultVisit(exprOpUn);
    }

    // Instructions, extends Stmt
    @Override
    public void visit(final StmtArrayAssign stmtArrayAssign) {
        this.defaultVisit(stmtArrayAssign);
    }

    @Override
    public void visit(final StmtAssign stmtAssign) {
        this.defaultVisit(stmtAssign);
    }

    @Override
    public void visit(final StmtBlock stmtBlock) {
        this.defaultVisit(stmtBlock);
    }

    @Override
    public void visit(final StmtIf stmtIf) {
        this.defaultVisit(stmtIf);
    }

    @Override
    public void visit(final StmtPrint stmtPrint) {
        this.defaultVisit(stmtPrint);
    }

    @Override
    public void visit(final StmtWhile stmtWhile) {
        this.defaultVisit(stmtWhile);
    }
}
