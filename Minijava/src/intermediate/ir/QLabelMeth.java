package intermediate.ir;

/**
 * <b>QLabelMeth : </b> <br>
 * Label arg1 <br>
 * Label for method
 */
public class QLabelMeth extends IRQuadruple {
	public QLabelMeth(IRVar arg1) {
		super(null, arg1, null, null);
	}
	
	@Override
	public String toString() {
		return this.arg1.getName() + ":";
	}
	
}
