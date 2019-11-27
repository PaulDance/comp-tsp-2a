package codegen;

import intermediate.IR;
import intermediate.ir.*;
import allocator.Allocator;

public class IR2MIPS {
  final Allocator allocator;
  final MIPS mips;
  private static final int S_REG_USED = 0;// need to save register $s0 to $sS_REG_USED
  private static final int T_REG_USED = 0;// need to save register $t0 to $tT_REG_USED

  public IR2MIPS(IR ir, Allocator allocator, MIPS mips)  {
    this.allocator = allocator;
    this.mips=mips;
    mips.println(".text");
    for (IRQuadruple q : ir.program) {
      mips.com(q.toString()); // put IR as comment
      accept(q);
    }
  }

  void accept(IRQuadruple q) {
    if (q instanceof QLabelMeth)     visit((QLabelMeth) q);
    else if (q instanceof QLabel)    visit((QLabel) q);
    else if (q instanceof QJump)     visit((QJump) q);
    else if (q instanceof QJumpCond) visit((QJumpCond) q);
    else if (q instanceof QReturn)   visit((QReturn) q);
    else if (q instanceof QParam)    visit((QParam) q);
    else if (q instanceof QCall)     visit((QCall) q);
    else if (q instanceof QNew)      visit((QNew) q);
    else if (q instanceof QCopy)     visit((QCopy) q);
    else if (q instanceof QAssign)   visit((QAssign) q);
    else if (q instanceof QAssignUnary)     visit((QAssignUnary) q);
    else if (q instanceof QNewArray)        visit((QNewArray) q);
    else if (q instanceof QAssignArrayFrom) visit((QAssignArrayFrom) q);
    else if (q instanceof QAssignArrayTo)   visit((QAssignArrayTo) q);
    else if (q instanceof QLength)   visit((QLength) q);
    else
      throw new main.CompilerException("IR2MIPS : unmanaged IR :" + q);
  }

  // // register helpers :Load and Store wuth respect to Variable Accss
  void regLoad(String reg, IRVar v) {
    mips.inst(allocator.access(v).load(reg));
  }

  void regLoadSaved(String reg, IRVar v) {
    mips.inst(allocator.access(v).loadSaved(reg));
  }

  void regStore(String reg, IRVar v) {
    mips.inst(allocator.access(v).store(reg));
  }

  // varargs for stack ; push/pop n register
  void push(String... regs) {
    int size = regs.length;
    if (size != 0)
      mips.inst("addi $sp, $sp, -" + 4 * size);
    for (int i = 0; i < size; i++)
      mips.inst("sw   " + regs[i] + ", " + 4 * (size - i - 1) + "($sp)");
  }

  void pop(String... regs) {
    int size = regs.length;
    for (int i = 0; i < size; i++)
      mips.inst("lw   " + regs[i] + ", " + 4 * (size - i - 1) + "($sp)");
    if (size != 0)
      mips.inst("addi $sp, $sp, " + 4 * size);
  }

  // ///// helpers : save/restore register (should be in allocator)
  // Callee-saved (QLabelMeth,QReturn) : $ra (+ $s0-7)
  void calleeIn() {
    mips.inst("sw   $ra ,  -4($fp)");
    for (int i = 0; i < S_REG_USED; i++)
      mips.inst("sw   $s" + i + " ,  -" + (4 * i + 8) + "($fp)");
  }

  void calleeOut() {
    mips.inst("lw   $ra ,  -4($fp)");
    for (int i = 0; i < S_REG_USED; i++)
      mips.inst("lw   $s" + i + " ,  -" + (4 * i + 8) + "($fp)");
  }

  // Caller save/restore (QCall) : $a0-3 (+ $t0-9)
  void callerSave() {
    push(tRegList());
    push("$fp", "$a3", "$a2", "$a1", "$a0");
  }

  void callerRestore() {
    pop("$fp", "$a3", "$a2", "$a1", "$a0");
    pop(tRegList());
  }

  String[] tRegList() {
    String[] s = new String[T_REG_USED];
    for (int i = 0; i < T_REG_USED; i++)
      s[i] = "$t" + i;
    return s;
  }

  // ////////////// VISIT ///////////////
  /** unknown Quadruple */
  void visit(IRQuadruple q) { throw new main.CompilerException("IR2MIPS : unmanaged IRQuadruple " + q);}

  /** <b>QLabel : </b> <br> Label arg1 */
  void visit(QLabel q) {
    mips.label(q.arg1.getName());
  }

  /* QParam/QCall helpers */
  private final IRVar[] params = new IRVar[42];
  private int indexParams = 0;

  protected int checkArgs(QCall q) { /* check Qparam/QCall consistancy */
    int nbArgs = Integer.parseInt(q.arg2.getName());
    if (nbArgs != indexParams)
      throw new main.CompilerException("IR2MIPS : Params error");
    indexParams = 0;
    return nbArgs;
  }

  protected IRVar getArg(int i) {
    if (indexParams != 0)
      throw new main.CompilerException("IR2MIPS : checkArgs() missing");
    return params[i];
  }

  // Qcall for special methods (static void)
  protected void specialCall(String function) {
    switch (function) {
    case "_system_exit" :
      mips.inst("li   $v0, 10");
      mips.inst("syscall");
      return;
    case "_system_out_println" :
      push("$a0");
      regLoad("$a0", getArg(0));
      mips.inst("jal  " + function);
      pop("$a0");
      return;
    case "main" :
      throw new main.CompilerException("IR2MIPS : recurse main forbidden");
    default :
      throw new main.CompilerException("IR2MIPS : undef void Method " + function);
    }
  }
  
  /** <b>QParam : </b> <br> Param arg1 */
  void visit(QParam q) {
    params[indexParams++] = q.arg1;
  }

  /** <b>QCall :</b> <br> result = call arg1 [numParams=arg2] */
  void visit(QCall q) {
    String function = q.arg1.getName();
    int nbArg = checkArgs(q);
    if (nbArg > 4)
      throw new main.CompilerException("IR2MIPS : too many args in method " + function);
    if (q.result == null) {
      specialCall(function);
      return;   
    }
    // else : common minijava methods
     
  }

}
