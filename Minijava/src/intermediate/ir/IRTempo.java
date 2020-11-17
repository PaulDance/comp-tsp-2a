package intermediate.ir;

/** Variable IR : Variable Temporaire. */
public class IRTempo implements IRVar {
    private final String name;
    private final String scope; // current method name

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getType() {
        return "IRTeno";
    }

    public String getScope() {
        return this.scope;
    }

    /**
     * Constructeur avec nom de variable autogénéré.
     *
     * @param scope Nom de la méthode courrante (en pratique)
     */
    public IRTempo(final String scope) {
        this.name = "t_" + IRTempo.index++;
        this.scope = scope;
    }

    private static int index = 0;

    /** Debug. */
    @Override
    public String toString() {
        return this.name;
    }
}
