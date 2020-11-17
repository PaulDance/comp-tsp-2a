package intermediate.ir;

/**
 * <b>QLabel : </b> <br>
 * Label arg1
 */
public class QLabel extends IRQuadruple {
    public QLabel(final IRVar arg1) {
        super(null, arg1, null, null);
    }

    @Override
    public String toString() {
        return this.arg1.getName() + ":";
    }
}
