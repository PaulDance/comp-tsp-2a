/** main() minimal pour CUP et JFlex
  * <li> Incompatible avec l'option <code>-locations</code> de CUP
  * <li> Intégrable directement dans une spec CUP (et la classe parser) :
  *       <code>parser code {:   public static void main()...  :} </code>
  */ 
public class CupParseMin {
 @SuppressWarnings("deprecation")
 public static void main(String[] args) throws Exception {
     if (args.length != 0) System.setIn(new java.io.FileInputStream(args[0]));
     new parser( new Yylex(new java.io.InputStreamReader(System.in))).parse();
    }
}
