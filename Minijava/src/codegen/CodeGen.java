package codegen;

import main.DEBUG;
import intermediate.IR;
import allocator.Allocator;

public class CodeGen {
  private String outfile;

  public String getResult() { return outfile; }

  public CodeGen(IR ir, String infile) {
    // outfile = "basneame(infile).mips"
    int dot = infile.lastIndexOf('.');
    if (dot != -1) infile = infile.substring(0, dot);
    this.outfile = infile + ".mips";

    // open a "MIPSWriter"
    MIPS mips = new MIPS(outfile);
    
    DEBUG.log("= Allocation Memoire");
    Allocator allocator = new Allocator(ir);
    
    DEBUG.log("= Traduction IR to MIPS -> " + outfile);
    new IR2MIPS(ir, allocator, mips);

    DEBUG.log("= Edition de lien : " + outfile + " -> " + outfile);
    new LinkRuntime(mips);

    // close Writer
    mips.close();
  }
}
