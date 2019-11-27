package intermediate.ir;


/** <b>QAssignArrayTo :</b> <br> result[arg2] = arg1 */
public class QAssignArrayTo extends IRQuadruple {

  public QAssignArrayTo(IRVar arg1, IRVar arg2, IRVar result) {
    super(null, arg1, arg2, result);
  }

  public String toString() {
    return result.getName() 
        + "[" + arg2.getName() + "]" 
        + " := " + arg1.getName();
  }
}
