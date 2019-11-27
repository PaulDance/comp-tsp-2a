package intermediate.ir;


/** <b>QCall :</b> <br> result = call arg1 [numParams=arg2] */
public class QCall extends IRQuadruple {
  public QCall(IRVar arg1, IRVar arg2, IRVar result) {
    super(null, arg1, arg2, result);
  }

  public String toString() {
    String temp;
    if (result == null) temp=""; // void
    else temp = result.getName() + " := ";
    return temp 
          + "call " + arg1.getName() 
          + "<" + arg2.getName() + ">";
   }
}
