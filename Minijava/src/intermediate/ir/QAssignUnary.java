package intermediate.ir;

import main.OPER;


/**
 * <b>QAssignUnary :</b> <br>
 * result = op arg1
 */
public class QAssignUnary extends IRQuadruple {
	public QAssignUnary(OPER op, IRVar arg1, IRVar result) {
		super(op, arg1, null, result);
	}
	
	@Override
	public String toString() {
		return this.result.getName() + " := " + this.op + " " + this.arg1.getName();
	}
}
