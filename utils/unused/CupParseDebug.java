import java.io.PrintWriter;

/** Racourci pour  CupParse -debug (ou autres customisations...) */
public class  CupParseDebug {
 
    public static void main(String[] args) { 
      String file =(args.length==0) ? "stdin" : args[0];
      boolean dump=true;   // dump les tokens lus
      boolean trace=false; // trace detail de l'analyse LR
      PrintWriter pw=new PrintWriter(System.out,true);

      new CupParse(file, dump, trace, pw);
    }

}
