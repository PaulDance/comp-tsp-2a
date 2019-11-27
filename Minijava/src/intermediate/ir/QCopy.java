package intermediate.ir;


/** <b>QCopy :</b> <br> result = arg1 */
public class QCopy extends IRQuadruple {

  public QCopy(IRVar arg1, IRVar result) {
    super(null, arg1, null, result);
  }

  public String toString() {
    return result.getName() + " := " + arg1.getName();
  }
}
