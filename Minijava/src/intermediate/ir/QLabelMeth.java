package intermediate.ir;


/** <b>QLabelMeth : </b> <br> Label arg1 
  <br> Label for method  */
public class QLabelMeth extends IRQuadruple{
  
  public QLabelMeth(IRVar arg1) {
    super(null, arg1, null, null);
  }

  public String toString() {
    return arg1.getName() + ":";
  }

}
