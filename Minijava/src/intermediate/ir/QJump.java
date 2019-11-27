package intermediate.ir;


/** <b>QJump :</b> <br> Jump arg1 */
public class QJump extends IRQuadruple {

  public QJump(IRVar arg1) {
    super(null, arg1, null, null);
  }

  public String toString() {
    return "goto " + arg1.getName();
  }
}
