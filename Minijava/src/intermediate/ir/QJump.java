package intermediate.ir;

/**
 * <b>QJump :</b> <br>
 * Jump arg1
 */
public class QJump extends IRQuadruple {
    public QJump(final IRVar arg1) {
        super(null, arg1, null, null);
    }

    @Override
    public String toString() {
        return "goto " + this.arg1.getName();
    }
}
