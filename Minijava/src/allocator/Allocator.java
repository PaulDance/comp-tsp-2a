package allocator;

import java.util.HashMap;
import main.*;
import intermediate.IR;
import intermediate.ir.IRConst;
import intermediate.ir.IRTempo;
import intermediate.ir.IRVar;
import semantic.symtab.*;

public class Allocator {
  private int globalSize; // size for global variables
  private final HashMap<String, Integer> classSize; // sizeOf classes, used in New
  private final HashMap<String, Integer> frameSize; // frameSize for methods,
  private final HashMap<IRVar, Access> access; // How to Load/Store with MIPS
  private final intermediate.IR ir;
  
  public Integer globalSize() {
    return globalSize;
  }

  public Integer classSize(String klassName) {
    return classSize.get(klassName);
  }

  public Integer frameSize(String methodName) {
    return frameSize.get(methodName);
  }

  public Access access(IRVar v) {
    return access.get(v);
  }

  // Constructor = compute Allocation
  public Allocator(IR ir) {
    this.globalSize = 0;
    this.classSize = new HashMap<>();
    this.frameSize = new HashMap<>();
    this.access = new HashMap<>();
    this.ir = ir;
    klassAlloc();
    methodAlloc();
    intermedAlloc();
    if (DEBUG.ALLOCATOR) {
      DEBUG.log(" globalSize (main) " + globalSize);
      DEBUG.log(" classSize " + classSize);
      DEBUG.log(" frameSize " + frameSize);
      DEBUG.log(" Access " + access);
    }
  }

  // ///// Instances de Classe
  // A extends B extends ... Object (extends null)
  // new A == [champs Object] ... [champs B][champs A]
  private Integer klassSize(InfoKlass kl) {
    if (kl == null)
      return 0;
    else
      return 4 * kl.getFields().size() 
          + klassSize(ir.rootScope.lookupKlass(kl.getParent()));
  }

  private void klassAlloc() {
    classSize.put(null, 0);   
    for (InfoKlass kl : ir.rootScope.getKlasses()) {
      if (kl == null)
        throw new CompilerException("Allocator : class==null");
      int off = klassSize(kl);
      classSize.put(kl.getName(), off);
      for (IRVar v: kl.getFields() ) {
        // Fields Access = Registre $a0(this) + off
        // minijava : fields only on this
        off -= 4;
        access.put(v, new AccessOff("$a0", off));
      }
    }
  }

  private void methodAlloc() {
    for (InfoKlass kl : ir.rootScope.getKlasses())
      for (InfoMethod m : kl.getMethods())
        methodAlloc(m);
  }

  // Method Frame +0 FP -4 SP
  // Argn Argn-1 ... Arg4 | $ra $s0-$s7 locals IRlocals | ..
  private void methodAlloc(InfoMethod m) {
    int frSize = 0;
    // fixed frame : save/restore $ra, $s0-$s7
    frSize += 4 + 4 * 8;
    // args : firsts in $ai, next in stack before FP
    int i = 0;
    final int ARGSMORE = 4;
    for ( IRVar v : m.getArgs()) {
      if (i < ARGSMORE)
        access.put(v, new AccessReg("$a" + i));
      else
        access.put(v, new AccessOff("$fp", 4 * (i - ARGSMORE)));
      i++;
    }
    // local vars in Frame
    // in sub scope
    for (IRVar v  : m.getLocals() ) {
      access.put(v, new AccessOff("$fp", -4 - frSize));
      frSize += 4;
    }
    // local variables in blocks not managed

    frameSize.put(m.getName(), frSize);
  }

  // // IR Variables : temporaire, constant=immediate
  private void intermedAlloc() {
    for (IRConst v : ir.consts)
      access.put(v, new AccessConst(v.getValue()));
    for (IRTempo v : ir.tempos) {
      String methName = v.getScope();
      if (methName.equals("main")) {
        access.put(v, new AccessOff("$gp", globalSize));
        globalSize += 4;
      } else {
        int frSize = frameSize.get(methName);
        access.put(v, new AccessOff("$fp", -4 - frSize));
        frSize += 4;
        frameSize.put(methName, frSize);
      }
    }
  }

  // /// Basic static register allocation (unused)
  private int regIndex = 0;

  public String allocateReg() {
    return regName(regIndex++);
  }

  private String regName(int i) {
    if (i < 10)
      return "$t" + i;
    if (i < 18)
      return "$s" + (i - 10);
    else
      throw new CompilerException("Allocator : Out of register");
  }
}
