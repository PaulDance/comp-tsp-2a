package intermediate.ir;


/** <b>QNew : </b> <br> result = new arg1 */
public class QNew extends IRQuadruple {

  public QNew(IRVar arg1, IRVar result) {
    super(null, arg1, null, result);
  }

  public String toString() {
    return result.getName() + " := new " + arg1.getName();
  }
}
