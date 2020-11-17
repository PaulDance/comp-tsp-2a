package intermediate.ir;

/**
 * <b>QLength :</b> <br>
 * result = length arg1
 */
public class QLength extends IRQuadruple {
    public QLength(final IRVar arg1, final IRVar result) {
        super(null, arg1, null, result);
    }

    @Override
    public String toString() {
        return this.result.getName() + " := length " + this.arg1.getName();
    }
}
