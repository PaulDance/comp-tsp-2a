package intermediate.ir;

import main.OPER;


/**
 * <b>QAssign :</b> <br>
 * result = arg1 op arg2
 */
public class QAssign extends IRQuadruple {
	public QAssign(OPER op, IRVar arg1, IRVar arg2, IRVar result) {
		super(op, arg1, arg2, result);
	}
	
	@Override
	public String toString() {
		return this.result.getName() + " := " + this.arg1.getName() + " " + this.op + " " + this.arg2.getName();
	}
}
