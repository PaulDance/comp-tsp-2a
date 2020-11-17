package semantic.trials;

import semantic.SemanticTree;
import syntax.ast.ASTVisitorDefault;
import syntax.ast.Formal;
import syntax.ast.Klass;
import syntax.ast.Method;
import syntax.ast.Var;


public class ListVars extends ASTVisitorDefault {
    private Klass currentKlass = null;
    private Method currentMethod = null;

    public ListVars(final SemanticTree semanticTree) {
        semanticTree.axiom.accept(this);
    }

    @Override
    public void visit(final Klass klass) {
        this.currentKlass = klass;
        klass.vars.accept(this);
        klass.methods.accept(this);
        this.currentKlass = null;
    }

    @Override
    public void visit(final Method method) {
        this.currentMethod = method;
        method.fargs.accept(this);
        method.vars.accept(this);
        this.currentMethod = null;
    }

    @Override
    public void visit(final Var var) {
        if (this.currentMethod != null) {
            System.out.print(
                    this.currentKlass.klassId.name + " class "
                    + this.currentMethod.returnType.name
                    + " " + this.currentMethod.methodId.name
                    + " method local variable: "
            );
        } else {
            System.out.print(this.currentKlass.klassId.name + " class attribute: ");
        }

        System.out.println(var.typeId.name + ' ' + var.varId.name);
    }

    @Override
    public void visit(final Formal formal) {
        System.out.print(
                this.currentKlass.klassId.name
                + " class " + this.currentMethod.returnType.name
                + " " + this.currentMethod.methodId.name
                + " method formal argument: "
        );
        System.out.println(formal.typeId.name + ' ' + formal.varId.name);
    }
}
