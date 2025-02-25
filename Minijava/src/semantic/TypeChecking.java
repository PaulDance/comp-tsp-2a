package semantic;

import main.DEBUG;
import main.OPER;
import main.TYPE;
import semantic.symtab.InfoKlass;
import semantic.symtab.InfoMethod;
import semantic.symtab.InfoVar;
import semantic.symtab.Scope;
import syntax.ast.ASTNode;
import syntax.ast.ASTVisitorDefault;
import syntax.ast.ExprArrayLength;
import syntax.ast.ExprArrayLookup;
import syntax.ast.ExprArrayNew;
import syntax.ast.ExprCall;
import syntax.ast.ExprIdent;
import syntax.ast.ExprLiteralBool;
import syntax.ast.ExprLiteralInt;
import syntax.ast.ExprNew;
import syntax.ast.ExprOpBin;
import syntax.ast.ExprOpUn;
import syntax.ast.Method;
import syntax.ast.StmtAssign;
import syntax.ast.StmtIf;
import syntax.ast.StmtPrint;
import syntax.ast.StmtWhile;
import syntax.ast.Type;


/**
 * Contrôle de Type.
 * <p>
 * Calcule l'attribut synthétisé Type : requis pour les noeuds Expr*
 * <p>
 * Verifie les contraintes de Typage de minijava
 */
public class TypeChecking extends ASTVisitorDefault {
    // Primitive Type names in Minijava.
    private static final String BOOL = TYPE.BOOL.toString();
    private static final String INT = TYPE.INT.toString();
    private static final String INT_ARRAY = TYPE.INT_ARRAY.toString();
    private static final String UNDEF = TYPE.UNDEF.toString();
    private static final String VOID = TYPE.VOID.toString();
    /**
     * Erreur de redéfinition dans la même portée.
     */
    private boolean error;
    private final SemanticTree semanticTree;

    public TypeChecking(final SemanticTree semanticTree) {
        this.error = false;
        this.semanticTree = semanticTree;
        semanticTree.axiom.accept(this);
    }

    /////////////////// Visit ////////////////////
    // Visites spécifiques : (non defaultVisit)
    // - Expr* : set Type
    // - Stmt* + Expr* (sauf exceptions) : Compatibilité des Types
    // - Type : Validité des noms de type dans déclarations ( Var, Method,
    /////////////////// Formal)
    // - Method : returnType compatible avec Type(returnExpr)
    // NB : validité des declarations de classe pré-requis (checkInheritance)

    @Override
    public void visit(final ExprLiteralBool exprLiteralBool) {
        this.setType(exprLiteralBool, TypeChecking.BOOL);
    }

    @Override
    public void visit(final ExprLiteralInt exprLiteralInt) {
        this.setType(exprLiteralInt, TypeChecking.INT);
    }

    @Override
    public void visit(final ExprArrayLength exprArrayLength) {
        this.defaultVisit(exprArrayLength);
        this.setType(exprArrayLength, TypeChecking.INT);
    }

    @Override
    public void visit(final ExprArrayLookup exprArrayLookup) {
        this.defaultVisit(exprArrayLookup);
        this.setType(exprArrayLookup, TypeChecking.INT);
    }

    @Override
    public void visit(final ExprNew exprNew) {
        if (this.lookupKlass(exprNew.klassId.name) == null) {
            this.reportError(exprNew,
                    String.format("Unknown type '%s' for instanciation '%s'.",
                            exprNew.klassId.name,
                            exprNew.toString()));
            this.setType(exprNew, TypeChecking.UNDEF);
        } else {
            this.setType(exprNew, exprNew.klassId.name);
        }
    }

    @Override
    public void visit(final ExprArrayNew exprArrayNew) {
        this.setType(exprArrayNew, TypeChecking.INT_ARRAY);
    }

    @Override
    public void visit(final ExprIdent exprIdent) {
        this.setType(exprIdent, this.lookupVarType(exprIdent, exprIdent.varId.name));
    }

    @Override
    public void visit(final ExprOpUn exprOpUn) {
        this.defaultVisit(exprOpUn);
        final String exprType = this.getType(exprOpUn.expr);

        if (exprOpUn.op == OPER.MINUS && !exprType.equals(TypeChecking.INT)
                || exprOpUn.op == OPER.NOT && !exprType.equals(TypeChecking.BOOL)) {
            this.reportError(exprOpUn,
                    String.format(
                            "Unary operator '%s' not applicable for expression '%s' of type '%s'.",
                            exprOpUn.op.toString(),
                            exprOpUn.expr.toString(),
                            exprType));
        } else {
            this.setType(exprOpUn, exprType);
        }
    }

    @Override
    public void visit(final ExprOpBin exprOpBin) {
        this.defaultVisit(exprOpBin);
        final String leftExprType = this.getType(exprOpBin.expr1);
        final String rightExprType = this.getType(exprOpBin.expr2);
        boolean typeError = true;

        if (exprOpBin.op == OPER.PLUS || exprOpBin.op == OPER.MINUS || exprOpBin.op == OPER.TIMES) {
            if (leftExprType.equals(TypeChecking.INT) && rightExprType.equals(TypeChecking.INT)) {
                this.setType(exprOpBin, TypeChecking.INT);
                typeError = false;
            }
        } else if (exprOpBin.op == OPER.LESS) {
            if (leftExprType.equals(TypeChecking.INT) && rightExprType.equals(TypeChecking.INT)) {
                this.setType(exprOpBin, TypeChecking.BOOL);
                typeError = false;
            }
        } else if (exprOpBin.op == OPER.AND) {
            if (leftExprType.equals(TypeChecking.BOOL) && rightExprType.equals(TypeChecking.BOOL)) {
                this.setType(exprOpBin, TypeChecking.BOOL);
                typeError = false;
            }
        } else {
            this.setType(exprOpBin, TypeChecking.UNDEF);
        }

        if (typeError) {
            this.reportError(exprOpBin,
                    String.format(
                            "Binary operator '%s' not applicable for expressions '%s' "
                                    + "and '%s' of respective types '%s' and '%s'.",
                            exprOpBin.op.toString(),
                            exprOpBin.expr1.toString(),
                            exprOpBin.expr2.toString(),
                            leftExprType,
                            rightExprType));
            this.setType(exprOpBin, TypeChecking.UNDEF);
        }
    }

    @Override
    public void visit(final ExprCall exprCall) {
        this.defaultVisit(exprCall);
        final InfoKlass receiverKlass = this.lookupKlass(this.getType(exprCall.receiver));

        if (receiverKlass == null) {
            this.reportError(exprCall,
                    String.format("Unknown class '%s' for method call receiver '%s'.",
                            this.getType(exprCall.receiver),
                            exprCall.receiver.toString()));
            this.setType(exprCall, TypeChecking.UNDEF);
        } else {
            final InfoMethod method = receiverKlass.getScope().lookupMethod(exprCall.methodId.name);

            if (method == null) {
                this.reportError(exprCall,
                        String.format("Unknown method '%s' in call '%s'.",
                                exprCall.methodId.name,
                                exprCall.toString()));
                this.setType(exprCall, TypeChecking.UNDEF);
            } else if (method.getArgs().length != exprCall.args.size() + 1) {
                this.reportError(exprCall,
                        String.format(
                                "Call of method '%s' has a wrong number of arguments: %d expected, but %d given.",
                                method.toString(),
                                method.getArgs().length - 1,
                                exprCall.args.size()));
                this.setType(exprCall, TypeChecking.UNDEF);
            } else {
                InfoVar argInfo;
                String argDeclaredType, argActualType;
                int i = 1;

                for (final ASTNode arg: exprCall.args) {
                    argInfo = method.getArgs()[i++];
                    argDeclaredType = argInfo.getType();
                    argActualType = this.getType(arg);
                    this.checkType(argDeclaredType,
                            argActualType,
                            String.format(
                                    "Call of method '%s' does not match signature: argument '%s' "
                                            + "has declared type '%s', but '%s' given instead.",
                                    method.toString(),
                                    argInfo.getName(),
                                    argDeclaredType,
                                    argActualType),
                            exprCall);
                }

                this.setType(exprCall, method.getReturnType());
            }
        }
    }

    @Override
    public void visit(final Method method) {
        this.defaultVisit(method);
        final String returnType = this.getType(method.returnExp);
        this.checkType(method.returnType.name,
                returnType,
                String.format("Invalid return type '%s' in method '%s %s', expected '%s' instead.",
                        returnType,
                        method.toString(),
                        method.methodId.name,
                        method.returnType.name),
                method);
    }

    @Override
    public void visit(final Type type) {
        this.defaultVisit(type);
        this.checkTypeName(type.name, type);
    }

    @Override
    public void visit(final StmtAssign stmtAssign) {
        this.defaultVisit(stmtAssign);

        final String receiverType = this.lookupVarType(stmtAssign, stmtAssign.varId.name);
        final String valueType = this.getType(stmtAssign.value);

        this.checkType(receiverType,
                valueType,
                String.format(
                        "Type mismatch in assignment: receiver '%s' "
                                + "is '%s' while value '%s' is '%s'.",
                        stmtAssign.varId.toString(),
                        receiverType,
                        stmtAssign.value.toString(),
                        valueType),
                stmtAssign);
    }

    @Override
    public void visit(final StmtIf stmtIf) {
        this.defaultVisit(stmtIf);
        final String conditionType = this.getType(stmtIf.test);
        this.checkType(TypeChecking.BOOL,
                conditionType,
                String.format("Non boolean condition: type is '%s'.", conditionType),
                stmtIf);
    }

    @Override
    public void visit(final StmtWhile stmtWhile) {
        this.defaultVisit(stmtWhile);
        final String conditionType = this.getType(stmtWhile.test);
        this.checkType(TypeChecking.BOOL,
                conditionType,
                String.format("Non boolean condition: type is '%s'.", conditionType),
                stmtWhile);
    }

    @Override
    public void visit(final StmtPrint stmtPrint) {
        this.defaultVisit(stmtPrint);
        final String argType = this.getType(stmtPrint.expr);
        this.checkType(TypeChecking.INT,
                argType,
                String.format("Can't print non-integer expression '%s' of type '%s'.",
                        stmtPrint.expr.toString(),
                        argType),
                stmtPrint);
    }

    /** Sortie en erreur. */
    public boolean getError() {
        return this.error;
    }

    // Helpers.

    private String getType(final ASTNode node) {
        return this.semanticTree.typeAttr.get(node);
    }

    private void setType(final ASTNode node, final String type) {
        this.semanticTree.typeAttr.set(node, type);
    }

    private Scope getScope(final ASTNode node) {
        return this.semanticTree.scopeAttr.get(node);
    }

    private InfoKlass lookupKlass(final String name) {
        return this.semanticTree.rootScope.lookupKlass(name);
    }

    /**
     * Compare type: returns true if t2 is sub-type of t1.
     */
    private boolean compareType(final String t1, final String t2) {
        if (t2 == null) {
            return false;
        } else if (t2.equals(t1)) {
            return true;
        } else {							// Sinon (t1 ancêtre de t2) ?
            final InfoKlass kl2 = this.lookupKlass(t2);

            if (kl2 != null) {
                return this.compareType(t1, kl2.getParent());
            }

            return false;				// NB : Suppose héritage valide !!!
        }
    }

    /**
     * Report an error.
     */
    private void reportError(final ASTNode where, final String msg) {
        DEBUG.logErr(where + " " + msg);
        this.error = true;
    }

    /**
     * Validation: "t2 sub-type of t1".
     */
    private void checkType(final String t1, final String t2, final String msg,
            final ASTNode where) {
        if (!this.compareType(t1, t2)) {
            this.reportError(where, "Wrong Type: " + t2 + " -> " + t1 + ";  " + msg);
        }
    }

    /**
     * Validation: TypeName is a valid Type.
     */
    private void checkTypeName(final String type, final ASTNode where) {
        if (!(type.equals(TypeChecking.BOOL) || type.equals(TypeChecking.INT)
                || type.equals(TypeChecking.INT_ARRAY) || type.equals(TypeChecking.VOID)
                || this.lookupKlass(type) != null)) {
            this.reportError(where, "Unknown Type: " + type);
        }
    }

    /**
     * Lookup symbolTable for variable type.
     */
    private String lookupVarType(final ASTNode node, final String name) {
        final InfoVar infoVar = this.getScope(node).lookupVariable(name);
        return infoVar == null ? TypeChecking.UNDEF : infoVar.getType();
    }
}
