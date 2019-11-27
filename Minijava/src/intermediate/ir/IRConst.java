package intermediate.ir;

/** Variable IR : Constante (litteral enrier) */
public class IRConst implements IRVar {

  public String getName() { return Integer.toString(getValue()); }
  public String getType() { return "IRConst"; }

  /** Valeut entière de la constante */
  public int getValue() { return value; } 
  private final Integer value;

  /** Constructeur */
  public IRConst(int value) {
    this.value = value;
  }

  /** Debug */
  @Override
  public String toString() {
    return getType() + " " + getName();
  } 

}
