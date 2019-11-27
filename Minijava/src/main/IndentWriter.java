package main;

/** Classe utilitaire pour impression avec indentation et utilisation de StringBuffer  
 * <br> <code>iw=new IdentWrite();
 * <br> iw.println(....); iw.print(...); iw.println();
 * <br> iw.indent(); iw.print(...); iw.println(...); iw.outdent();
 * <br> System.out.print(iw);
 * </cpde>
 */
public class IndentWriter {
  private static final String TAB = "  ";

  private StringBuilder sb;
  private int indent;
  private boolean startOfLine;

  // constructeur
  public IndentWriter() {
    this.sb = new StringBuilder();
    this.indent = 0;
    this.startOfLine = true;
  }

  // resultat
  @Override
  public String toString() { return sb.toString(); }

  // methodes utiles
  public void indent()  { indent++; }
  public void outdent() { indent--; }

  public void print(Object o) { print(o.toString()); }

  public void print(String s) {
    if (startOfLine)
      for (int i = 0; i < indent; i++)
        sb.append(TAB);
    startOfLine = false;
    sb.append(s);
  }

  public void println(Object o) { println(o.toString()); }

  public void println(String s) { print(s); println(); }

  public void println() {
    sb.append(System.lineSeparator());
    startOfLine = true;
  }
}
