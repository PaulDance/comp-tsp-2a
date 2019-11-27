package intermediate.ir;

/**
 * <b>QLength :</b> <br>
 * result = length arg1
 */
public class QLength extends IRQuadruple {
	public QLength(IRVar arg1, IRVar result) {
		super(null, arg1, null, result);
	}
	
	@Override
	public String toString() {
		return this.result.getName() + " := length " + this.arg1.getName();
	}
}
