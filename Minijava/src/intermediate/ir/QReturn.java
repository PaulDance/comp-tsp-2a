package intermediate.ir;


/** <b>QReturn :</b> <br> Return arg1 */
public class QReturn extends IRQuadruple {

  public QReturn(IRVar arg1) {
    super(null, arg1, null, null);
  }

  public String toString() {
    return "return " + arg1.getName();
  }
}
