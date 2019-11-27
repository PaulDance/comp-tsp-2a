package intermediate.ir;


/** <b>QJumpCond : </b> <br> Jump arg1 IfNot arg2 */
public class QJumpCond extends IRQuadruple {

  public QJumpCond(IRVar arg1, IRVar arg2) {
    super(null, arg1, arg2, null);
  }

  public String toString() {
    return "iffalse " + arg2.getName() + " goto " + arg1.getName();
  }
}
