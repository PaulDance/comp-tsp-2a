package intermediate.ir;

/** Variable IR : Label */
public class IRLabel implements IRVar {
  private final String name;

  public String getName() { return name; }
  public String getType() { return "IRLabel"; }
  
  /** Constructeur avec nom fixé. En pratique nom de methode pour QCall  */
  public IRLabel(String name) {
    this.name=name;
  }

  /** Constructeur avec nom autogénéré. */
  public IRLabel() {
    this.name = "L_" + index++;
  }
  private static int index = 0;

  /** Debug */
  @Override
  public String toString() {
    return name;
  }}
