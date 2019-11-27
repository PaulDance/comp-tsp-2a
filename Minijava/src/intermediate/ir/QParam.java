package intermediate.ir;


/** <b>QParam : </b> <br> Param arg1 */
public class QParam extends IRQuadruple {

  public QParam(IRVar arg1) {
    super(null, arg1, null, null);
  }

  public String toString() {
    return "param " + arg1.getName();
  }
}
