package syntax;

import main.IndentWriter;
import syntax.ast.*;

/** PrettyPrint minijava par visite de l'AST */
public class PrettyPrint extends ASTVisitorDefault {
  // Printing with indentation
  private IndentWriter out;

  // Constructeur
  public PrettyPrint(ASTNode axiom) {
    this.out = new IndentWriter();
    axiom.accept(this); // => visite((Axiom)axiom)
  }

  // Result
  public String toString() {
    return out.toString();
  }

  /////////////////// Visit ////////////////////
  // non default visit = all Nodes
  
  // helper : visit a comma-separated list
  private void visitCommaList(ASTNode n) {
    boolean first = true;
    for (ASTNode f : n) {
      if (first)
        first = false;
      else
        out.print(", ");
      f.accept(this);
    }
  }

  // Productions de base, extends ASTNode 
  @Override
  public void visit(Axiom n) {
    defaultVisit(n);
  }
 
  @Override
  public void visit(Klass n) {
    out.println("");
    out.print("class ");
    n.klassId.accept(this);
    out.print(" extends ");
    n.parentId.accept(this);
    out.println(" { ");
    out.indent();
    n.vars.accept(this);
    n.methods.accept(this);
    out.outdent();
    out.println("}");
  }

  @Override
  public void visit(KlassMain n) {
    out.print("class ");
    n.klassId.accept(this);
    out.println(" {");
    out.indent();
    out.print("public static void main (String [] ");
    n.argId.accept(this);
    out.println(") {");
    out.indent();
    n.stmt.accept(this);
    out.outdent();
    out.println("}");
    out.outdent();
    out.println("}");
  }

  @Override
  public void visit(Method n) {
    out.println("");
    out.print("public ");
    n.returnType.accept(this);
    out.print(" ");
    n.methodId.accept(this);
    out.print(" (");
    visitCommaList(n.fargs);
    out.println(") { ");
    out.indent();
    n.vars.accept(this);
    n.stmts.accept(this);
    out.print("return ");
    n.returnExp.accept(this);
    out.println(";");
    out.outdent();
    out.println("}");
  }

  @Override
  public void visit(Formal n) {
    n.typeId.accept(this);
    out.print(" ");
    n.varId.accept(this);
  }

  @Override
  public void visit(Ident n) {
    out.print(n.name);
  }

  @Override
  public void visit(Type n) {
    out.print(n.name);
  }

  @Override
  public void visit(Var n) {
    n.typeId.accept(this);
    out.print(" ");
    n.varId.accept(this);
    out.println(";");
  }

  // Expressions , extends Expr
  @Override
  public void visit(ExprArrayLength n) {
    n.array.accept(this);
    out.print(".length");
  }

  @Override
  public void visit(ExprArrayLookup n) {
    n.array.accept(this);
    out.print("[");
    n.index.accept(this);
    out.print("]");
  }

  @Override
  public void visit(ExprArrayNew n) {
    out.print("new int [");
    n.size.accept(this);
    out.print("]");
  }

  @Override
  public void visit(ExprCall n) {
    n.receiver.accept(this);
    out.print(".");
    n.methodId.accept(this);
    out.print("(");
    visitCommaList(n.args);
    out.print(")");
  }

  @Override
  public void visit(ExprLiteralBool n) {
    out.print(n.value);
  }

  @Override
  public void visit(ExprLiteralInt n) {
    out.print(n.value);
  }

  @Override
  public void visit(ExprNew n) {
    out.print("new ");
    n.klassId.accept(this);
    out.print("()");
  }

  @Override
  public void visit(ExprOpBin n) {
    out.print("(");
    n.expr1.accept(this);
    out.print(" " + n.op + " ");
    n.expr2.accept(this);
    out.print(")");
  }

  @Override
  public void visit(ExprOpUn n) {
    out.print(n.op + " ");
    n.expr.accept(this);
  }

  // Instructions, extends Stmt
  @Override
  public void visit(StmtArrayAssign n) {
    n.arrayId.accept(this);
    out.print("[");
    n.index.accept(this);
    out.print("] = ");
    n.value.accept(this);
    out.println(";");
  }

  @Override
  public void visit(StmtAssign n) {
    n.varId.accept(this);
    out.print(" = ");
    n.value.accept(this);
    out.println(";");
  }

  @Override
  public void visit(StmtBlock n) {
    switch (n.stmts.size() + n.vars.size()) {
      case 0:
        out.println("{ }");
        break;
      case 1:
        n.vars.accept(this);
        n.stmts.accept(this);
        break;
      default:
        out.println("{");
        out.indent();
        n.vars.accept(this);
        n.stmts.accept(this);
        out.outdent();
        out.println("}");
    }
  }

  @Override
  public void visit(StmtIf n) {
    out.print("if (");
    n.test.accept(this);
    out.print(") ");
    n.ifTrue.accept(this);
    out.print("else ");
    n.ifFalse.accept(this);
  }

  @Override
  public void visit(StmtPrint n) {
    out.print("System.out.println(");
    n.expr.accept(this);
    out.println(");");
  }

  @Override
  public void visit(StmtWhile n) {
    out.print("while (");
    n.test.accept(this);
    out.print(") ");
    n.body.accept(this);
  }
}
