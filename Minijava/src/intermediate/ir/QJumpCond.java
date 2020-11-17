package intermediate.ir;

/**
 * <b>QJumpCond : </b> <br>
 * Jump arg1 IfNot arg2
 */
public class QJumpCond extends IRQuadruple {
    public QJumpCond(final IRVar arg1, final IRVar arg2) {
        super(null, arg1, arg2, null);
    }

    @Override
    public String toString() {
        return "iffalse " + this.arg2.getName() + " goto " + this.arg1.getName();
    }
}
