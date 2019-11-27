package codegen;

import allocator.Allocator;
import intermediate.IR;
import intermediate.ir.IRQuadruple;
import intermediate.ir.IRVar;
import intermediate.ir.QAssign;
import intermediate.ir.QAssignArrayFrom;
import intermediate.ir.QAssignArrayTo;
import intermediate.ir.QAssignUnary;
import intermediate.ir.QCall;
import intermediate.ir.QCopy;
import intermediate.ir.QJump;
import intermediate.ir.QJumpCond;
import intermediate.ir.QLabel;
import intermediate.ir.QLabelMeth;
import intermediate.ir.QLength;
import intermediate.ir.QNew;
import intermediate.ir.QNewArray;
import intermediate.ir.QParam;
import intermediate.ir.QReturn;


public class IR2MIPS {
	final Allocator allocator;
	final MIPS mips;
	private static final int S_REG_USED = 0;// need to save register $s0 to
											// $sS_REG_USED
	private static final int T_REG_USED = 0;// need to save register $t0 to
											// $tT_REG_USED
	
	public IR2MIPS(IR ir, Allocator allocator, MIPS mips) {
		this.allocator = allocator;
		this.mips = mips;
		mips.println(".text");
		
		for (IRQuadruple q: ir.program) {
			mips.com(q.toString()); // put IR as comment
			this.accept(q);
		}
	}
	
	void accept(IRQuadruple q) {
		if (q instanceof QLabelMeth) {
			this.visit(q);
		}
		else if (q instanceof QLabel) {
			this.visit((QLabel) q);
		}
		else if (q instanceof QJump) {
			this.visit(q);
		}
		else if (q instanceof QJumpCond) {
			this.visit(q);
		}
		else if (q instanceof QReturn) {
			this.visit(q);
		}
		else if (q instanceof QParam) {
			this.visit((QParam) q);
		}
		else if (q instanceof QCall) {
			this.visit((QCall) q);
		}
		else if (q instanceof QNew) {
			this.visit(q);
		}
		else if (q instanceof QCopy) {
			this.visit(q);
		}
		else if (q instanceof QAssign) {
			this.visit(q);
		}
		else if (q instanceof QAssignUnary) {
			this.visit(q);
		}
		else if (q instanceof QNewArray) {
			this.visit(q);
		}
		else if (q instanceof QAssignArrayFrom) {
			this.visit(q);
		}
		else if (q instanceof QAssignArrayTo) {
			this.visit(q);
		}
		else if (q instanceof QLength) {
			this.visit(q);
		}
		else {
			throw new main.CompilerException("IR2MIPS : unmanaged IR :" + q);
		}
	}
	
	// // register helpers :Load and Store wuth respect to Variable Accss
	void regLoad(String reg, IRVar v) {
		this.mips.inst(this.allocator.access(v).load(reg));
	}
	
	void regLoadSaved(String reg, IRVar v) {
		this.mips.inst(this.allocator.access(v).loadSaved(reg));
	}
	
	void regStore(String reg, IRVar v) {
		this.mips.inst(this.allocator.access(v).store(reg));
	}
	
	// varargs for stack ; push/pop n register
	void push(String... regs) {
		int size = regs.length;
		
		if (size != 0) {
			this.mips.inst("addi $sp, $sp, -" + 4 * size);
		}
		
		for (int i = 0; i < size; i++) {
			this.mips.inst("sw   " + regs[i] + ", " + 4 * (size - i - 1) + "($sp)");
		}
	}
	
	void pop(String... regs) {
		int size = regs.length;
		
		for (int i = 0; i < size; i++) {
			this.mips.inst("lw   " + regs[i] + ", " + 4 * (size - i - 1) + "($sp)");
		}
		
		if (size != 0) {
			this.mips.inst("addi $sp, $sp, " + 4 * size);
		}
	}
	
	// ///// helpers : save/restore register (should be in allocator)
	// Callee-saved (QLabelMeth,QReturn) : $ra (+ $s0-7)
	void calleeIn() {
		this.mips.inst("sw   $ra ,  -4($fp)");
		
		for (int i = 0; i < S_REG_USED; i++) {
			this.mips.inst("sw   $s" + i + " ,  -" + (4 * i + 8) + "($fp)");
		}
	}
	
	void calleeOut() {
		this.mips.inst("lw   $ra ,  -4($fp)");
		for (int i = 0; i < S_REG_USED; i++) {
			this.mips.inst("lw   $s" + i + " ,  -" + (4 * i + 8) + "($fp)");
		}
	}
	
	// Caller save/restore (QCall) : $a0-3 (+ $t0-9)
	void callerSave() {
		this.push(this.tRegList());
		this.push("$fp", "$a3", "$a2", "$a1", "$a0");
	}
	
	void callerRestore() {
		this.pop("$fp", "$a3", "$a2", "$a1", "$a0");
		this.pop(this.tRegList());
	}
	
	String[] tRegList() {
		String[] s = new String[T_REG_USED];
		
		for (int i = 0; i < T_REG_USED; i++) {
			s[i] = "$t" + i;
		}
		
		return s;
	}
	
	// ////////////// VISIT ///////////////
	/** unknown Quadruple */
	void visit(IRQuadruple q) {
		throw new main.CompilerException("IR2MIPS : unmanaged IRQuadruple " + q);
	}
	
	/**
	 * <b>QLabel : </b> <br>
	 * Label arg1
	 */
	void visit(QLabel q) {
		this.mips.label(q.arg1.getName());
	}
	
	/* QParam/QCall helpers */
	private final IRVar[] params = new IRVar[42];
	private int indexParams = 0;
	
	protected int checkArgs(QCall q) { /* check Qparam/QCall consistancy */
		int nbArgs = Integer.parseInt(q.arg2.getName());
		
		if (nbArgs != this.indexParams) {
			throw new main.CompilerException("IR2MIPS : Params error");
		}
		
		this.indexParams = 0;
		return nbArgs;
	}
	
	protected IRVar getArg(int i) {
		if (this.indexParams != 0) {
			throw new main.CompilerException("IR2MIPS : checkArgs() missing");
		}
		
		return this.params[i];
	}
	
	// Qcall for special methods (static void)
	protected void specialCall(String function) {
		switch (function) {
			case "_system_exit":
				this.mips.inst("li   $v0, 10");
				this.mips.inst("syscall");
				return;
				
			case "_system_out_println":
				this.push("$a0");
				this.regLoad("$a0", this.getArg(0));
				this.mips.inst("jal  " + function);
				this.pop("$a0");
				return;
				
			case "main":
				throw new main.CompilerException("IR2MIPS : recurse main forbidden");
				
			default:
				throw new main.CompilerException("IR2MIPS : undef void Method " + function);
		}
	}
	
	/**
	 * <b>QParam : </b> <br>
	 * Param arg1
	 */
	void visit(QParam q) {
		this.params[this.indexParams++] = q.arg1;
	}
	
	/**
	 * <b>QCall :</b> <br>
	 * result = call arg1 [numParams=arg2]
	 */
	void visit(QCall q) {
		String function = q.arg1.getName();
		int nbArg = this.checkArgs(q);
		
		if (nbArg > 4) {
			throw new main.CompilerException("IR2MIPS : too many args in method " + function);
		}
		
		if (q.result == null) {
			this.specialCall(function);
			return;
		}
		// else : common minijava methods
	}
}
