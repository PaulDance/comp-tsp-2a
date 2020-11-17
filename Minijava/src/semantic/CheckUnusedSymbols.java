package semantic;

import java.util.ArrayList;
import java.util.Collection;

import semantic.symtab.InfoVar;
import syntax.ast.ASTVisitorDefault;
import syntax.ast.ExprIdent;


public class CheckUnusedSymbols extends ASTVisitorDefault {
    private final boolean error;
    private final SemanticTree semanticTree;
    public final ArrayList<InfoVar> unusedVars;

    public CheckUnusedSymbols(final SemanticTree semanticTree) {
        this.error = false;
        this.semanticTree = semanticTree;
        this.unusedVars = new ArrayList<InfoVar>();
        final Collection<InfoVar> allVars = this.semanticTree.rootScope.getAllVariables();

        for (final InfoVar infoVar: allVars) {
            if (!(infoVar.getType().equals("Object") && infoVar.getName().equals("o"))
                    && !infoVar.getName().equals("this")) {
                this.unusedVars.add(infoVar);
            }
        }

        this.semanticTree.axiom.accept(this);
    }

    @Override
    public void visit(final ExprIdent exprIdent) {
        this.unusedVars.remove(
                this.semanticTree.scopeAttr.get(exprIdent).lookupVariable(exprIdent.varId.name));
    }

    public boolean getError() {
        return this.error;
    }
}
