package main;

/** Runtime Exception de Compilation */
public class CompilerException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CompilerException(final String message) {
        super(message);
    }
}
