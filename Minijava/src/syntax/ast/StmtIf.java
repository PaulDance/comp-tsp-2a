package syntax.ast;

/** Instruction if then else. <p>{@link #test}<br>{@link #ifTrue}<br>{@link #ifFalse} */
public class StmtIf extends Stmt {
  public final Expr test; 
  public final Stmt ifTrue;
  public final Stmt ifFalse;

  public StmtIf(Expr test, Stmt ifTrue, Stmt ifFalse) {
    super(test, ifTrue, ifFalse);
    this.test = test;
    this.ifTrue = ifTrue;
    this.ifFalse = ifFalse;
  }

  public void accept(ASTVisitor v) { v.visit(this); }
}
