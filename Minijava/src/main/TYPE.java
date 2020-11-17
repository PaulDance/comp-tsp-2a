package main;

/** Enumeration des types primitifs et mapping toString() */
public enum TYPE {
    VOID("void"), BOOL("boolean"), INT("int"), INT_ARRAY("int[]"), UNDEF("undef");

    private final String name;

    private TYPE(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
