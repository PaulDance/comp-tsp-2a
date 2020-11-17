package intermediate.ir;

/**
 * <b>QAssignArrayTo :</b> <br>
 * result[arg2] = arg1
 */
public class QAssignArrayTo extends IRQuadruple {
    public QAssignArrayTo(final IRVar arg1, final IRVar arg2, final IRVar result) {
        super(null, arg1, arg2, result);
    }

    @Override
    public String toString() {
        return this.result.getName() + "[" + this.arg2.getName() + "]" + " := "
                + this.arg1.getName();
    }
}
