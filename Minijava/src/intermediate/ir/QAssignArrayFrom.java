package intermediate.ir;

/**
 * <b>QAssignArrayFrom :</b> <br>
 * result = arg1[arg2]
 */
public class QAssignArrayFrom extends IRQuadruple {
	public QAssignArrayFrom(IRVar arg1, IRVar arg2, IRVar result) {
		super(null, arg1, arg2, result);
	}
	
	@Override
	public String toString() {
		return this.result.getName() + " := " + this.arg1.getName() + "[" + this.arg2.getName() + "]";
	}
}
