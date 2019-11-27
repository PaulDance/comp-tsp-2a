package syntax.ast;

/** Instruction : classse abstraite pour Stmt*. */
public abstract class Stmt extends ASTNode {
	Stmt(ASTNode... fils) {
		super(fils);
	}
}
