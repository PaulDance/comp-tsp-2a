package codegen;

import allocator.Allocator;
import intermediate.IR;
import main.DEBUG;


public class CodeGen {
	private String outfile;
	
	public String getResult() {
		return this.outfile;
	}
	
	public CodeGen(IR ir, String infile) {
		// outfile = "basneame(infile).mips"
		int dot = infile.lastIndexOf('.');
		
		if (dot != -1) {
			infile = infile.substring(0, dot);
		}
		
		this.outfile = infile + ".mips";
		
		// open a "MIPSWriter"
		MIPS mips = new MIPS(this.outfile);
		
		DEBUG.log("= Allocation Memoire");
		Allocator allocator = new Allocator(ir);
		
		DEBUG.log("= Traduction IR to MIPS -> " + this.outfile);
		new IR2MIPS(ir, allocator, mips);
		
		DEBUG.log("= Edition de lien : " + this.outfile + " -> " + this.outfile);
		new LinkRuntime(mips);
		
		// close Writer
		mips.close();
	}
}
