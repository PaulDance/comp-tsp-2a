package intermediate.ir;

/**
 * <b>QCopy :</b> <br>
 * result = arg1
 */
public class QCopy extends IRQuadruple {
    public QCopy(final IRVar arg1, final IRVar result) {
        super(null, arg1, null, result);
    }

    @Override
    public String toString() {
        return this.result.getName() + " := " + this.arg1.getName();
    }
}
