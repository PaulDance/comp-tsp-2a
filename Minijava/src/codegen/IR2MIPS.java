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
import main.CompilerException;


public class IR2MIPS {
	final Allocator allocator;
	final MIPS mips;
	private static final int S_REG_USED = 0;	// need to save register $s0 to $sS_REG_USED.
	private static final int T_REG_USED = 0;	// need to save register $t0 to $tT_REG_USED.
	
	public IR2MIPS(IR ir, Allocator allocator, MIPS mips) {
		this.allocator = allocator;
		this.mips = mips;
		mips.println(".text");
		
		for (IRQuadruple irQuadruple: ir.program) {
			mips.com(irQuadruple.toString());	// put IR as comment
			this.accept(irQuadruple);
		}
	}
	
	void accept(IRQuadruple irQuadruple) {
		if (irQuadruple instanceof QLabelMeth) {
			this.visit((QLabelMeth) irQuadruple);
		}
		else if (irQuadruple instanceof QLabel) {
			this.visit((QLabel) irQuadruple);
		}
		else if (irQuadruple instanceof QJump) {
			this.visit((QJump) irQuadruple);
		}
		else if (irQuadruple instanceof QJumpCond) {
			this.visit((QJumpCond) irQuadruple);
		}
		else if (irQuadruple instanceof QReturn) {
			this.visit((QReturn) irQuadruple);
		}
		else if (irQuadruple instanceof QParam) {
			this.visit((QParam) irQuadruple);
		}
		else if (irQuadruple instanceof QCall) {
			this.visit((QCall) irQuadruple);
		}
		else if (irQuadruple instanceof QNew) {
			this.visit((QNew) irQuadruple);
		}
		else if (irQuadruple instanceof QCopy) {
			this.visit((QCopy) irQuadruple);
		}
		else if (irQuadruple instanceof QAssign) {
			this.visit((QAssign) irQuadruple);
		}
		else if (irQuadruple instanceof QAssignUnary) {
			this.visit((QAssignUnary) irQuadruple);
		}
		else if (irQuadruple instanceof QNewArray) {
			this.visit((QNewArray) irQuadruple);
		}
		else if (irQuadruple instanceof QAssignArrayFrom) {
			this.visit((QAssignArrayFrom) irQuadruple);
		}
		else if (irQuadruple instanceof QAssignArrayTo) {
			this.visit((QAssignArrayTo) irQuadruple);
		}
		else if (irQuadruple instanceof QLength) {
			this.visit((QLength) irQuadruple);
		}
		else {
			throw new CompilerException("IR2MIPS : unmanaged IR :" + irQuadruple);
		}
	}
	
	void visit(QCopy qCopy) {
		final String r0 = this.virtReg(qCopy.arg1, "$v0");
		this.regLoad(r0, qCopy.arg1);
		
		this.regStore(r0, qCopy.result);
	}
	
	void visit(QJump qJump) {
		this.mips.jump(qJump.arg1.getName());
	}
	
	void visit(QJumpCond qJumpCond) {
		final String r0 = this.virtReg(qJumpCond.arg2, "$v0");
		this.regLoad(r0, qJumpCond.arg2);
		
		this.mips.jump(qJumpCond.arg1.getName(), r0);
	}
	
	void visit(QAssignUnary qAssignUnary) {
		final String r0 = this.virtReg(qAssignUnary.arg1, "$v0");
		this.regLoad(r0, qAssignUnary.arg1);
		
		this.mips.oper("$v0", r0, qAssignUnary.op);
		this.regStore("$v0", qAssignUnary.result);
	}
	
	void visit(QAssign qAssign) {
		final String r0 = this.virtReg(qAssign.arg1, "$v0"),
					r1 = this.virtReg(qAssign.arg2, "$v1");
		
		this.regLoad(r0, qAssign.arg1);
		this.regLoad(r1, qAssign.arg2);
		
		this.mips.oper("$v0", r0, qAssign.op, r1);
		
		this.regStore("$v0", qAssign.result);
	}
	
	void visit(QAssignArrayFrom qAssignArrayFrom) {
		final String r0 = this.virtReg(qAssignArrayFrom.arg1, "$v0"),
					r1 = this.virtReg(qAssignArrayFrom.arg2, "$v1");
		this.regLoad(r0, qAssignArrayFrom.arg1);				// Address of the array.
		this.regLoad(r1, qAssignArrayFrom.arg2);				// Index of the element.
		
		this.mips.add("$v1", r1, 1);
		this.mips.fois4("$v1");									// Compute the address of the element.
		this.mips.add("$v1", "$v1", r0);
		
		this.mips.load("$v0", 0, "$v1");
		this.regStore("$v0", qAssignArrayFrom.result);			// Return the result value.
	}
	
	void visit(QAssignArrayTo qAssignArrayTo) {
		final String r1 = this.virtReg(qAssignArrayTo.result, "$v1"),
					r0 = this.virtReg(qAssignArrayTo.arg1, "$v0"),
					r2 = this.virtReg(qAssignArrayTo.arg2, "$t0");
		this.regLoad(r1, qAssignArrayTo.result);				// Address of the array.
		this.regLoad(r0, qAssignArrayTo.arg1);					// Element to assign.
		this.regLoad(r2, qAssignArrayTo.arg2);					// Index of the array element.
		
		this.mips.add("$t0", r2, 1);
		this.mips.fois4("$t0");									// Compute the address of the element.
		this.mips.add("$t0", r1);
		
		this.mips.store(r0, 0, "$t0");							// Return the result value.
	}
	
	void visit(QLength qLength) {
		final String r0 = this.virtReg(qLength.arg1, "$v0");
		this.regLoad(r0, qLength.arg1);
		
		this.mips.load("$v0", 0, r0);
		this.regStore("$v0", qLength.result);
	}
	
	void visit(QNew qNew) {
		this.push("$a0");
		
		this.mips.load("$a0", this.allocator.classSize(qNew.arg1.getName()));
		this.mips.jumpAdr("_new_object");
		this.regStore("$v0", qNew.result);
		
		this.pop("$a0");
	}
	
	void visit(QNewArray qNewArray) {
		final String r0 = this.virtReg(qNewArray.arg2, "$a0");
		this.push("$a0");
		
		this.regLoad(r0, qNewArray.arg2);
		this.mips.move("$v1", r0);
		this.mips.add("$a0", r0, 1);							// Get and compute the number of bytes,
		this.mips.fois4("$a0");
		
		this.mips.jumpAdr("_new_object");
		
		this.mips.store("$v1", 0, "$v0");						// then store the length in the first element.
		this.regStore("$v0", qNewArray.result);
		
		this.pop("$a0");
	}
	
	/**
	 * <b>QLabel : </b> <br>
	 * Label arg1
	 */
	void visit(QLabel qLabel) {
		this.mips.label(qLabel.arg1.getName());
	}

	void visit(QLabelMeth qLabelMeth) {
		this.mips.label(qLabelMeth.arg1.getName());
		this.calleeIn();
	}
	
	/**
	 * <b>QParam : </b> <br>
	 * Param arg1
	 */
	void visit(QParam qParam) {
		this.params[this.indexParams++] = qParam.arg1;
	}
	
	/**
	 * <b>QCall :</b> <br>
	 * result = call arg1 [numParams=arg2]
	 */
	void visit(QCall qCall) {
		final String functionName = qCall.arg1.getName();
		final int argsNb = this.checkArgs(qCall);
		
		if (argsNb > 4) {
			throw new CompilerException("IR2MIPS : too many args in method " + functionName);
		}
		else if (qCall.result == null) {
			this.specialCall(functionName);
		}
		else {
			this.callerSave();
			
			for (int i = 0; i < 4 && i < argsNb; i++) {
				this.regLoadSaved("$a" + i, this.getArg(i));
			}
			
			this.mips.move("$fp", "$sp");
			this.mips.add("$sp", -this.allocator.frameSize(functionName));
			
			this.mips.jumpAdr(functionName);
			
			this.mips.move("$sp", "$fp");
			this.callerRestore();
			this.regStore("$v0", qCall.result);
		}
	}
	
	void visit(QReturn qReturn) {
		this.calleeOut();
		this.regLoad("$v0", qReturn.arg1);
		this.mips.retour();
	}
	
	/** Unknown Quadruple */
	void visit(IRQuadruple irQuadruple) {
		throw new CompilerException("IR2MIPS : unmanaged IRQuadruple " + irQuadruple);
	}
	
	
	// Register helpers: Load and Store with respect to Variable Access.
	private String virtReg(IRVar var, String defReg) {
		String reg = this.allocator.access(var).getRegister();
		return reg == null ? defReg : reg;
	}
	
	void regLoad(String reg, IRVar v) {
		this.mips.inst(this.allocator.access(v).load(reg));
	}
	
	void regLoadSaved(String reg, IRVar v) {
		this.mips.inst(this.allocator.access(v).loadSaved(reg));
	}
	
	void regStore(String reg, IRVar v) {
		this.mips.inst(this.allocator.access(v).store(reg));
	}
	
	/**
	 * VarArgs for stack ; push/pop n register.
	 */
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
	
	// Helpers : save/restore register (should be in allocator).
	/**
	 * Caller save/restore (QCall) : $a0-3 (+ $t0-9).
	 */
	void callerSave() {
		this.push(this.tRegList());
		this.push("$fp", "$a3", "$a2", "$a1", "$a0");
	}
	
	void callerRestore() {
		this.pop("$fp", "$a3", "$a2", "$a1", "$a0");
		this.pop(this.tRegList());
	}
	
	/**
	 * Called-saved (QLabelMeth,QReturn) : $ra (+ $s0-7).
	 */
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
	
	String[] tRegList() {
		String[] s = new String[T_REG_USED];
		
		for (int i = 0; i < T_REG_USED; i++) {
			s[i] = "$t" + i;
		}
		
		return s;
	}
	
	/* QParam/QCall helpers */
	private final IRVar[] params = new IRVar[42];
	private int indexParams = 0;
	
	protected int checkArgs(QCall q) { /* check Qparam/QCall consistancy */
		int nbArgs = Integer.parseInt(q.arg2.getName());
		
		if (nbArgs != this.indexParams) {
			throw new CompilerException("IR2MIPS : Params error");
		}
		
		this.indexParams = 0;
		return nbArgs;
	}
	
	protected IRVar getArg(int i) {
		if (this.indexParams != 0) {
			throw new CompilerException("IR2MIPS : checkArgs() missing");
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
				throw new CompilerException("IR2MIPS : recurse main forbidden");
				
			default:
				throw new CompilerException("IR2MIPS : undef void Method " + function);
		}
	}
}
