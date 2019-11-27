package codegen;

import java.io.*;
import main.OPER;
import main.CompilerException;

/** Classe utilitaire d'impression MIPS, "Etend" PrintWriter*/
public class MIPS {
  private PrintWriter pw;

  public MIPS(String outfile) {
    try {
      this.pw = new PrintWriter(new FileWriter(outfile));
    } catch (IOException e) {
      throw new CompilerException(e.getMessage());
    }    
  }

  public void close() { pw.close(); }

  // impression de base : ligne, label, instruction, commantaire(s)
  public void println(String s) {
    if (s != null)
      pw.println(s);
  }
  
  public void label(String s) {
    if (s != null)
      pw.println(s + ":");
  }

  public void inst(String s) {
    if (s != null)
      pw.println("\t" + s);
  }
  
  public void com(String s) {
    if (s != null)
      pw.println("   # " + s);
  }
 

  // Des instructions predefinies
  public void load(String r0, int offset, String r1) {
    inst("lw   " + r0 + ", " + offset + "(" + r1 + ")");
  }

  public void load(String r0, int immediate) {
    inst("li   " + r0 + ", " + immediate);
  }

  public void move(String r0, String r1) {
    inst("move " + r0 + ", " + r1);
    // == "addiu "+ r0 +", " + r1 +", 0"
  }

  public void store(String r0, int offset, String r1) {
    inst("lw   " + r0 + ", " + offset + "(" + r1 + ")");
  }

  public void jump(String name, String r0) {
    inst("beq  " + r0 + ", $zero, " + name);
  }

  public void jump(String name) {
    inst("j    " + name);
  }

  public void jumpAdr(String name) {
    inst("jal  " + name);
  }

  public void retour() {
    inst("jr $ra");
  }

  public void oper(String r0, OPER op, String r1) {
    switch (op) {
      case PLUS:
        inst("add  " + r0 + ", " + r0 + ", " + r1);
        break;
      case MINUS:
        inst("sub  " + r0 + ", " + r0 + ", " + r1);
        break;
      case TIMES:
        inst("mult " + r0 + ", " + r1);
        inst("mflo " + r0);
        break;
      case AND:
        inst("and  " + r0 + ", " + r0 + ", " + r1);
        break;
      case LESS:
        inst("slt  " + r0 + ", " + r0 + ", " + r1);
        break;
      default:
        inst("BAD OP " + op);  
   }
  }

  public void oper(String r0, main.OPER op) {
    if (op==OPER.NOT)
      inst("seq  " + r0 + ", $zero, " + r0);
    else
      inst("BAD OP " + op);  
  }

  public void add(String r0, int immediate) {
    inst("addi " + r0 + ", " + r0 + ", " + immediate);
  }

  public void fois4(String r0) {
    inst("sll  " + r0 + ", " + r0 + ", 2");
  }
}
