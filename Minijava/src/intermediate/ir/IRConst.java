package intermediate.ir;

/** Variable IR : Constante (litteral enrier) */
public class IRConst implements IRVar {
    @Override
    public String getName() {
        return Integer.toString(this.getValue());
    }

    @Override
    public String getType() {
        return "IRConst";
    }

    /** Valeut entière de la constante */
    public int getValue() {
        return this.value;
    }

    private final Integer value;

    /** Constructeur */
    public IRConst(final int value) {
        this.value = value;
    }

    /** Debug */
    @Override
    public String toString() {
        return this.getType() + " " + this.getName();
    }

}
