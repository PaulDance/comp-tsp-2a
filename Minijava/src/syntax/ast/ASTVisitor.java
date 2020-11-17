package syntax.ast;

/** Interface Visiteur pour l'AST Minijava. */
public interface ASTVisitor {
    // Liste homogéne, extends ASTNode
    <T extends ASTNode> void visit(ASTList<T> nodeList);

    // Productions de base, extends ASTNode
    void visit(Axiom axiom);

    void visit(Klass klass);

    void visit(KlassMain klassMain);

    void visit(Method method);

    void visit(Formal formal);

    void visit(Ident ident);

    void visit(Type type);

    void visit(Var var);

    // Expressions , extends Expr
    void visit(ExprArrayLength exprArrayLength);

    void visit(ExprArrayLookup exprArrayLookup);

    void visit(ExprArrayNew exprArrayNew);

    void visit(ExprCall exprCall);

    void visit(ExprIdent exprIdent);

    void visit(ExprLiteralBool exprLiteralBool);

    void visit(ExprLiteralInt exprLiteralInt);

    void visit(ExprNew exprNew);

    void visit(ExprOpBin exprOpBin);

    void visit(ExprOpUn exprOpUn);

    // Instructions, extends Stmt
    void visit(StmtArrayAssign stmtArrayAssign);

    void visit(StmtAssign stmtAssign);

    void visit(StmtBlock stmtBlock);

    void visit(StmtIf stmtIf);

    void visit(StmtPrint stmtPrint);

    void visit(StmtWhile stmtWhile);
}
