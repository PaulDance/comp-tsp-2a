package syntax;

import main.IndentWriter;
import syntax.ast.ASTNode;
import syntax.ast.ASTVisitorDefault;
import syntax.ast.Axiom;
import syntax.ast.ExprArrayLength;
import syntax.ast.ExprArrayLookup;
import syntax.ast.ExprArrayNew;
import syntax.ast.ExprCall;
import syntax.ast.ExprLiteralBool;
import syntax.ast.ExprLiteralInt;
import syntax.ast.ExprNew;
import syntax.ast.ExprOpBin;
import syntax.ast.ExprOpUn;
import syntax.ast.Formal;
import syntax.ast.Ident;
import syntax.ast.Klass;
import syntax.ast.KlassMain;
import syntax.ast.Method;
import syntax.ast.StmtArrayAssign;
import syntax.ast.StmtAssign;
import syntax.ast.StmtBlock;
import syntax.ast.StmtIf;
import syntax.ast.StmtPrint;
import syntax.ast.StmtWhile;
import syntax.ast.Type;
import syntax.ast.Var;


/** PrettyPrint minijava par visite de l'AST */
public class PrettyPrint extends ASTVisitorDefault {
    // Printing with indentation
    private final IndentWriter out;

    // Constructeur
    public PrettyPrint(final ASTNode axiom) {
        this.out = new IndentWriter();
        axiom.accept(this); // => visite((Axiom)axiom)
    }

    // Result
    @Override
    public String toString() {
        return this.out.toString();
    }

    /////////////////// Visit ////////////////////
    // non default visit = all Nodes

    // helper : visit a comma-separated list
    private void visitCommaList(final ASTNode n) {
        boolean first = true;

        for (final ASTNode f: n) {
            if (first) {
                first = false;
            } else {
                this.out.print(", ");
            }

            f.accept(this);
        }
    }

    // Productions de base, extends ASTNode
    @Override
    public void visit(final Axiom n) {
        this.defaultVisit(n);
    }

    @Override
    public void visit(final Klass n) {
        this.out.println("");
        this.out.print("class ");
        n.klassId.accept(this);
        this.out.print(" extends ");
        n.parentId.accept(this);
        this.out.println(" { ");
        this.out.indent();
        n.vars.accept(this);
        n.methods.accept(this);
        this.out.outdent();
        this.out.println("}");
    }

    @Override
    public void visit(final KlassMain n) {
        this.out.print("class ");
        n.klassId.accept(this);
        this.out.println(" {");
        this.out.indent();
        this.out.print("public static void main (String [] ");
        n.argId.accept(this);
        this.out.println(") {");
        this.out.indent();
        n.stmt.accept(this);
        this.out.outdent();
        this.out.println("}");
        this.out.outdent();
        this.out.println("}");
    }

    @Override
    public void visit(final Method n) {
        this.out.println("");
        this.out.print("public ");
        n.returnType.accept(this);
        this.out.print(" ");
        n.methodId.accept(this);
        this.out.print(" (");
        this.visitCommaList(n.fargs);
        this.out.println(") { ");
        this.out.indent();
        n.vars.accept(this);
        n.stmts.accept(this);
        this.out.print("return ");
        n.returnExp.accept(this);
        this.out.println(";");
        this.out.outdent();
        this.out.println("}");
    }

    @Override
    public void visit(final Formal n) {
        n.typeId.accept(this);
        this.out.print(" ");
        n.varId.accept(this);
    }

    @Override
    public void visit(final Ident n) {
        this.out.print(n.name);
    }

    @Override
    public void visit(final Type n) {
        this.out.print(n.name);
    }

    @Override
    public void visit(final Var n) {
        n.typeId.accept(this);
        this.out.print(" ");
        n.varId.accept(this);
        this.out.println(";");
    }

    // Expressions , extends Expr
    @Override
    public void visit(final ExprArrayLength n) {
        n.array.accept(this);
        this.out.print(".length");
    }

    @Override
    public void visit(final ExprArrayLookup n) {
        n.array.accept(this);
        this.out.print("[");
        n.index.accept(this);
        this.out.print("]");
    }

    @Override
    public void visit(final ExprArrayNew n) {
        this.out.print("new int [");
        n.size.accept(this);
        this.out.print("]");
    }

    @Override
    public void visit(final ExprCall n) {
        n.receiver.accept(this);
        this.out.print(".");
        n.methodId.accept(this);
        this.out.print("(");
        this.visitCommaList(n.args);
        this.out.print(")");
    }

    @Override
    public void visit(final ExprLiteralBool n) {
        this.out.print(n.value);
    }

    @Override
    public void visit(final ExprLiteralInt n) {
        this.out.print(n.value);
    }

    @Override
    public void visit(final ExprNew n) {
        this.out.print("new ");
        n.klassId.accept(this);
        this.out.print("()");
    }

    @Override
    public void visit(final ExprOpBin n) {
        this.out.print("(");
        n.expr1.accept(this);
        this.out.print(" " + n.op + " ");
        n.expr2.accept(this);
        this.out.print(")");
    }

    @Override
    public void visit(final ExprOpUn n) {
        this.out.print(n.op + " ");
        n.expr.accept(this);
    }

    // Instructions, extends Stmt
    @Override
    public void visit(final StmtArrayAssign n) {
        n.arrayId.accept(this);
        this.out.print("[");
        n.index.accept(this);
        this.out.print("] = ");
        n.value.accept(this);
        this.out.println(";");
    }

    @Override
    public void visit(final StmtAssign n) {
        n.varId.accept(this);
        this.out.print(" = ");
        n.value.accept(this);
        this.out.println(";");
    }

    @Override
    public void visit(final StmtBlock n) {
        switch (n.stmts.size() + n.vars.size()) {
            case 0:
                this.out.println("{ }");
                break;

            case 1:
                n.vars.accept(this);
                n.stmts.accept(this);
                break;

            default:
                this.out.println("{");
                this.out.indent();
                n.vars.accept(this);
                n.stmts.accept(this);
                this.out.outdent();
                this.out.println("}");
        }
    }

    @Override
    public void visit(final StmtIf n) {
        this.out.print("if (");
        n.test.accept(this);
        this.out.print(") ");
        n.ifTrue.accept(this);
        this.out.print("else ");
        n.ifFalse.accept(this);
    }

    @Override
    public void visit(final StmtPrint n) {
        this.out.print("System.out.println(");
        n.expr.accept(this);
        this.out.println(");");
    }

    @Override
    public void visit(final StmtWhile n) {
        this.out.print("while (");
        n.test.accept(this);
        this.out.print(") ");
        n.body.accept(this);
    }
}
