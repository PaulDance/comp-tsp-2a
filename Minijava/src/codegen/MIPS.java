package codegen;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import main.CompilerException;
import main.OPER;


/** Classe utilitaire d'impression MIPS, "Etend" PrintWriter */
public class MIPS {
	private PrintWriter pw;
	
	public MIPS(String outfile) {
		try {
			this.pw = new PrintWriter(new FileWriter(outfile));
		}
		catch (IOException e) {
			throw new CompilerException(e.getMessage());
		}
	}
	
	public void close() {
		this.pw.close();
	}
	
	// impression de base : ligne, label, instruction, commantaire(s)
	public void println(String s) {
		if (s != null) {
			this.pw.println(s);
		}
	}
	
	public void label(String s) {
		if (s != null) {
			this.pw.println(s + ":");
		}
	}
	
	public void inst(String s) {
		if (s != null) {
			this.pw.println("\t" + s);
		}
	}
	
	public void com(String s) {
		if (s != null) {
			this.pw.println("   # " + s);
		}
	}
	
	// Des instructions prédéfinies
	public void load(String r0, int offset, String r1) {
		this.inst("lw   " + r0 + ", " + offset + "(" + r1 + ")");
	}
	
	public void load(String r0, int immediate) {
		this.inst("li   " + r0 + ", " + immediate);
	}
	
	public void move(String r0, String r1) {
		this.inst("move " + r0 + ", " + r1);
		// == "addiu "+ r0 +", " + r1 +", 0"
	}
	
	public void store(String r0, int offset, String r1) {
		this.inst("lw   " + r0 + ", " + offset + "(" + r1 + ")");
	}
	
	public void jump(String name, String r0) {
		this.inst("beq  " + r0 + ", $zero, " + name);
	}
	
	public void jump(String name) {
		this.inst("j    " + name);
	}
	
	public void jumpAdr(String name) {
		this.inst("jal  " + name);
	}
	
	public void retour() {
		this.inst("jr $ra");
	}
	
	public void oper(String r0, OPER op, String r1) {
		switch (op) {
			case PLUS:
				this.inst("add  " + r0 + ", " + r0 + ", " + r1);
				break;
				
			case MINUS:
				this.inst("sub  " + r0 + ", " + r0 + ", " + r1);
				break;
				
			case TIMES:
				this.inst("mult " + r0 + ", " + r1);
				this.inst("mflo " + r0);
				break;
				
			case AND:
				this.inst("and  " + r0 + ", " + r0 + ", " + r1);
				break;
				
			case LESS:
				this.inst("slt  " + r0 + ", " + r0 + ", " + r1);
				break;
				
			default:
				this.inst("BAD OP " + op);
		}
	}
	
	public void oper(String r0, main.OPER op) {
		if (op == OPER.NOT) {
			this.inst("seq  " + r0 + ", $zero, " + r0);
		}
		else {
			this.inst("BAD OP " + op);
		}
	}
	
	public void add(String r0, int immediate) {
		this.inst("addi " + r0 + ", " + r0 + ", " + immediate);
	}
	
	public void fois4(String r0) {
		this.inst("sll  " + r0 + ", " + r0 + ", 2");
	}
}
