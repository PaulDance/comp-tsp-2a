package intermediate.ir;

/**
 * <b>QReturn :</b> <br>
 * Return arg1
 */
public class QReturn extends IRQuadruple {
    public QReturn(final IRVar arg1) {
        super(null, arg1, null, null);
    }

    @Override
    public String toString() {
        return "return " + this.arg1.getName();
    }
}
