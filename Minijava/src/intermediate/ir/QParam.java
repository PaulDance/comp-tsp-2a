package intermediate.ir;

/**
 * <b>QParam : </b> <br>
 * Param arg1
 */
public class QParam extends IRQuadruple {
    public QParam(final IRVar arg1) {
        super(null, arg1, null, null);
    }

    @Override
    public String toString() {
        return "param " + this.arg1.getName();
    }
}
