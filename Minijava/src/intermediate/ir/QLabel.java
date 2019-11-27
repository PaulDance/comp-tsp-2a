package intermediate.ir;


/** <b>QLabel : </b> <br> Label arg1 */
public class QLabel extends IRQuadruple {

  public QLabel(IRVar arg1) {
    super(null, arg1, null, null);
  }

  public String toString() {
    return arg1.getName() + ":";
  }
}
