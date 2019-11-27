package intermediate.ir;

/**
 * <b>QNewArray : </b> <br>
 * result = new arg1 [Size=arg2]
 */
public class QNewArray extends IRQuadruple {
	public QNewArray(IRVar arg1, IRVar arg2, IRVar result) {
		super(null, arg1, arg2, result);
	}
	
	@Override
	public String toString() {
		return this.result.getName() + " := new " + this.arg1.getName() + "<" + this.arg2.getName() + ">";
	}
}
