package intermediate.ir;

/**
 * <b>QCall :</b> <br>
 * result = call arg1 [numParams=arg2]
 */
public class QCall extends IRQuadruple {
	public QCall(IRVar arg1, IRVar arg2, IRVar result) {
		super(null, arg1, arg2, result);
	}
	
	@Override
	public String toString() {
		String temp;
		
		if (this.result == null) {
			temp = ""; // void
		}
		else {
			temp = this.result.getName() + " := ";
		}
		
		return temp + "call " + this.arg1.getName() + "<" + this.arg2.getName() + ">";
	}
}
