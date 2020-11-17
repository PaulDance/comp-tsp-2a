package main;

/** Enumeration des operateurs Binaires et Unaires, et mapping toString(). */
public enum OPER {
    PLUS("+"), MINUS("-"), TIMES("*"), AND("&&"), LESS("<"), NOT("!"), UNDEF("undef");

    private final String name;

    private OPER(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
