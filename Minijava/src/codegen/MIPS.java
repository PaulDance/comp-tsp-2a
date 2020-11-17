package codegen;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import main.CompilerException;
import main.OPER;


/** Classe utilitaire d'impression MIPS, "Etend" PrintWriter */
public class MIPS {
    private PrintWriter pw;

    public MIPS(final String outfile) {
        try {
            this.pw = new PrintWriter(new FileWriter(outfile));
        } catch (final IOException e) {
            throw new CompilerException(e.getMessage());
        }
    }

    public void close() {
        this.pw.close();
    }

    // impression de base : ligne, label, instruction, commantaire(s)
    public void println(final String s) {
        if (s != null) {
            this.pw.println(s);
        }
    }

    public void label(final String s) {
        if (s != null) {
            this.pw.println(s + ":");
        }
    }

    public void inst(final String s) {
        if (s != null) {
            this.pw.println("\t" + s);
        }
    }

    public void com(final String s) {
        if (s != null) {
            this.pw.println("   # " + s);
        }
    }

    // Des instructions prédéfinies
    public void load(final String r0, final int offset, final String r1) {
        this.inst("lw   " + r0 + ", " + offset + "(" + r1 + ")");
    }

    public void load(final String r0, final int immediate) {
        this.inst("li   " + r0 + ", " + immediate);
    }

    public void move(final String r0, final String r1) {
        this.inst("move " + r0 + ", " + r1);
    }

    public void store(final String r0, final int offset, final String r1) {
        this.inst("sw   " + r0 + ", " + offset + "(" + r1 + ")");
    }

    public void jump(final String name, final String r0) {
        this.inst("beq  " + r0 + ", $zero, " + name);
    }

    public void jump(final String name) {
        this.inst("j    " + name);
    }

    public void jumpAdr(final String name) {
        this.inst("jal  " + name);
    }

    public void retour() {
        this.inst("jr $ra");
    }

    /**
     * Generates the MIPS instruction(s) necessary to correctly compute the
     * binary operation {@code r0 op r1}. This version uses {@code r0} by
     * default to store the result of the operation. See
     * {@link #oper( String, String, OPER, String)} to specify that.
     *
     * @param r0 The first operand's register.
     * @param op The binary operator.
     * @param r1 The second operand's register.
     */
    public void oper(final String r0, final OPER op, final String r1) {
        this.oper(r0, r0, op, r1);
    }

    /**
     * Generates the MIPS instruction(s) necessary to correctly compute the
     * binary operation {@code r0 op r1}. This version allows one to explicitly
     * specify the register which the result should be stored in, instead of
     * doing that using {@code r0}.
     *
     * @param resReg The result register: where should the result be stored in.
     * @param r0     The first operand's register.
     * @param op     The binary operator.
     * @param r1     The second operand's register.
     * @see          #oper(String, OPER, String)
     */
    public void oper(final String resReg, final String r0, final OPER op, final String r1) {
        switch (op) {
            case PLUS:
                this.inst("add  " + resReg + ", " + r0 + ", " + r1);
                break;

            case MINUS:
                this.inst("sub  " + resReg + ", " + r0 + ", " + r1);
                break;

            case TIMES:
                this.inst("mult " + r0 + ", " + r1);
                this.inst("mflo " + resReg);
                break;

            case AND:
                this.inst("and  " + resReg + ", " + r0 + ", " + r1);
                break;

            case LESS:
                this.inst("slt  " + resReg + ", " + r0 + ", " + r1);
                break;

            default:
                this.inst("BAD OP " + op);
        }
    }

    /**
     * Generates the MIPS instruction(s) necessary to correctly compute the
     * binary operation {@code op r0}. This version uses {@code r0} by default
     * to store the result of the operation. See
     * {@link #oper( String, String, OPER)} to specify that.
     *
     * @param r0 The operand's register.
     * @param op The unary operator.
     */
    public void oper(final String r0, final main.OPER op) {
        this.oper(r0, r0, op);
    }

    /**
     * Generates the MIPS instruction(s) necessary to correctly compute the
     * binary operation {@code op r0}. This version allows one to explicitly
     * specify the register which the result should be stored in, instead of
     * doing that using {@code r0}.
     *
     * @param resReg The result register: where should the result be stored in.
     * @param r0     The operand's register.
     * @param op     The unary operator.
     * @see          #oper(String, OPER)
     */
    public void oper(final String resReg, final String r0, final main.OPER op) {
        if (op == OPER.NOT) {
            this.inst("seq  " + resReg + ", $zero, " + r0);
        } else {
            this.inst("BAD OP " + op);
        }
    }

    public void add(final String r0, final String r1) {
        this.add(r0, r0, r1);
    }

    public void add(final String resReg, final String r0, final String r1) {
        this.inst("add  " + resReg + ", " + r0 + ", " + r1);
    }

    public void add(final String r0, final int immediate) {
        this.add(r0, r0, immediate);
    }

    public void add(final String resReg, final String r0, final int immediate) {
        this.inst("addi " + resReg + ", " + r0 + ", " + immediate);
    }

    public void fois4(final String r0) {
        this.fois4(r0, r0);
    }

    public void fois4(final String resReg, final String r0) {
        this.inst("sll  " + resReg + ", " + r0 + ", 2");
    }

    /**
     * Produces the three MIPS instruction that transform an array index to a
     * usable heap memory address.
     *
     * @param resReg       The result register: where should the result be
     *                     stored in.
     * @param elIndexReg   The register storing the index of the element.
     * @param arrayAddrReg The register storing the array's memory address.
     */
    public void arrayIndexToAddr(final String resReg, final String elIndexReg,
            final String arrayAddrReg) {
        this.add(resReg, elIndexReg, 1);
        this.fois4(resReg);
        this.add(resReg, resReg, arrayAddrReg);
    }

    public void checkArrayIndexInBounds(final String elIndexReg, final String arrayAddrReg) {
        this.load("$t0", 0, arrayAddrReg);
        this.add("$t1", elIndexReg, 1);
        this.inst("sltu $t2, $zero, $t1");
        this.inst("sltu $t3, " + elIndexReg + ", $t0");
        this.inst("and  $t4, $t2, $t3");
        this.jump("_array_index_out_of_bounds_exc", "$t4");
    }
}
