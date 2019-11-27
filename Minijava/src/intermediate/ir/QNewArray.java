package intermediate.ir;


/** <b>QNewArray : </b> <br> result = new arg1 [Size=arg2] */
public class QNewArray extends IRQuadruple {

  public QNewArray(IRVar arg1, IRVar arg2, IRVar result) {
    super(null, arg1, arg2, result);
  }

  public String toString() {
    return result.getName()
        + " := new " + arg1.getName()
        + "<" + arg2.getName() + ">";
  }
}
