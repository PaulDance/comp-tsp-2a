package allocator;

import java.util.HashMap;

import intermediate.IR;
import intermediate.ir.IRConst;
import intermediate.ir.IRTempo;
import intermediate.ir.IRVar;
import main.CompilerException;
import main.DEBUG;
import semantic.symtab.InfoKlass;
import semantic.symtab.InfoMethod;


public class Allocator {
    private int globalSize; // size for global variables
    private final HashMap<String, Integer> classSize; // sizeOf classes, used in
                                                      // New
    private final HashMap<String, Integer> frameSize; // frameSize for methods,
    private final HashMap<IRVar, Access> access; // How to Load/Store with MIPS
    private final intermediate.IR ir;

    public Integer globalSize() {
        return this.globalSize;
    }

    public Integer classSize(String klassName) {
        return this.classSize.get(klassName);
    }

    public Integer frameSize(String methodName) {
        return this.frameSize.get(methodName);
    }

    public Access access(IRVar v) {
        return this.access.get(v);
    }

    // Constructor = compute Allocation
    public Allocator(IR ir) {
        this.globalSize = 0;
        this.classSize = new HashMap<>();
        this.frameSize = new HashMap<>();
        this.access = new HashMap<>();
        this.ir = ir;
        this.klassAlloc();
        this.methodAlloc();
        this.intermedAlloc();

        if (DEBUG.ALLOCATOR) {
            DEBUG.log(" globalSize (main) " + this.globalSize);
            DEBUG.log(" classSize " + this.classSize);
            DEBUG.log(" frameSize " + this.frameSize);
            DEBUG.log(" Access " + this.access);
        }
    }

    // ///// Instances de Classe
    // A extends B extends ... Object (extends null)
    // new A == [champs Object] ... [champs B][champs A]
    private Integer klassSize(InfoKlass kl) {
        if (kl == null) {
            return 0;
        } else {
            return 4 * kl.getFields().size()
                    + this.klassSize(this.ir.rootScope.lookupKlass(kl.getParent()));
        }
    }

    private void klassAlloc() {
        this.classSize.put(null, 0);

        for (InfoKlass kl: this.ir.rootScope.getKlasses()) {
            if (kl == null) {
                throw new CompilerException("Allocator : class==null");
            }

            int off = this.klassSize(kl);
            this.classSize.put(kl.getName(), off);

            for (IRVar v: kl.getFields()) {
                // Fields Access = Registre $a0(this) + off
                // minijava : fields only on this
                off -= 4;
                this.access.put(v, new AccessOff("$a0", off));
            }
        }
    }

    private void methodAlloc() {
        for (InfoKlass kl: this.ir.rootScope.getKlasses()) {
            for (InfoMethod m: kl.getMethods()) {
                this.methodAlloc(m);
            }
        }
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

        for (IRVar v: m.getArgs()) {
            if (i < ARGSMORE) {
                this.access.put(v, new AccessReg("$a" + i));
            } else {
                this.access.put(v, new AccessOff("$fp", 4 * (i - ARGSMORE)));
            }

            i++;
        }
        // local vars in Frame
        // in sub scope
        for (IRVar v: m.getScope().getAllVariables()) {
            this.access.put(v, new AccessOff("$fp", -4 - frSize));
            frSize += 4;
        }
        // local variables in blocks not managed

        this.frameSize.put(m.getName(), frSize);
    }

    // // IR Variables : temporaire, constant=immediate
    private void intermedAlloc() {
        for (IRConst v: this.ir.consts) {
            this.access.put(v, new AccessConst(v.getValue()));
        }

        for (IRTempo v: this.ir.tempos) {
            String methName = v.getScope();

            if (methName.equals("main")) {
                this.access.put(v, new AccessOff("$gp", this.globalSize));
                this.globalSize += 4;
            } else {
                int frSize = this.frameSize.get(methName);
                this.access.put(v, new AccessOff("$fp", -4 - frSize));
                frSize += 4;
                this.frameSize.put(methName, frSize);
            }
        }
    }

    // /// Basic static register allocation (unused)
    private int regIndex = 0;

    public String allocateReg() {
        return this.regName(this.regIndex++);
    }

    private String regName(int i) {
        if (i < 10) {
            return "$t" + i;
        }

        if (i < 18) {
            return "$s" + (i - 10);
        } else {
            throw new CompilerException("Allocator : Out of register");
        }
    }
}
