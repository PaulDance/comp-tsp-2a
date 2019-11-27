package intermediate.ir;

/**
 * <b>QAssignArrayTo :</b> <br>
 * result[arg2] = arg1
 */
public class QAssignArrayTo extends IRQuadruple {
	public QAssignArrayTo(IRVar arg1, IRVar arg2, IRVar result) {
		super(null, arg1, arg2, result);
	}
	
	@Override
	public String toString() {
		return this.result.getName() + "[" + this.arg2.getName() + "]" + " := " + this.arg1.getName();
	}
}
