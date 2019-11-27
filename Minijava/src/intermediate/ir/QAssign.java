package intermediate.ir;

import main.OPER;

/** <b>QAssign :</b> <br> result = arg1 op arg2 */
public class QAssign extends IRQuadruple {

  public QAssign(OPER op, IRVar arg1, IRVar arg2, IRVar result) {
    super(op, arg1, arg2, result);
  }

  public String toString() {
    return result.getName() + " := " 
        + arg1.getName() 
        + " " + op + " "
        + arg2.getName();
  }
}
