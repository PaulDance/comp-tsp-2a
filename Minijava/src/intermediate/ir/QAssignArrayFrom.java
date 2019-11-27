package intermediate.ir;


/** <b>QAssignArrayFrom :</b> <br> result = arg1[arg2] */
public class QAssignArrayFrom extends IRQuadruple {

  public QAssignArrayFrom(IRVar arg1, IRVar arg2, IRVar result) {
    super(null, arg1, arg2, result);
  }

  public String toString() {
    return result.getName() + " := " 
        + arg1.getName() 
        + "[" + arg2.getName() + "]";
  }
}
