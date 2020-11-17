
/**
 * Utilities meant to help pretty-print the parsed JSON value. The result is
 * displayed indented using tabulations. In order to do that, each parsed
 * pattern handles its own indentation using {@link #sameIndent()} and can
 * modify the global indentation level with {@link #incIndent()} or
 * {@link #decIndent()}. Other methods are implemented here in order to reduce
 * the code quantity necessary in the pattern recognition specification
 * "JSON.cup".
 * 
 * @author Paul Mabileau &lt;paulmabileau@hotmail.fr&gt;
 */
public class JsonPrettyPrintingUtils {
    /**
     * Stores the current level of indentation as a number of tabulation
     * characters.
     */
    private long currentIndent = 0;
    /**
     * Indicates whether the next parsed pattern will be allowed to be indented
     * or not.
     */
    private boolean indentNext = true;
    /**
     * The {@link StringBuilder} used to store the pretty-print result
     * progressively in order to display it only when the parsed input is
     * syntaxically correct.
     */
    private final StringBuilder outputBuilder = new StringBuilder();

    /**
     * Finishes and returns the string meant to be the output.
     * 
     * @return The string that was previously built.
     */
    public String getOutput() {
        return this.outputBuilder.toString();
    }

    /**
     * Appends the given character to the output builder.
     * 
     * @param chr A character to append to the output builder.
     */
    public void out(final char chr) {
        this.outputBuilder.append(chr);
    }

    /**
     * Simple shortcut to System.out.print(char).
     * 
     * @param chr A character to print on the standard output.
     */
    public void print(final char chr) {
        System.out.print(chr);
    }

    /**
     * Calls {@code out(chr)} then {@code out('\n')}.
     * 
     * @param chr A character to add at the end of the output, followed by a
     *            newline.
     * @see       #out(char)
     */
    public void outln(final char chr) {
        this.out(chr);
        this.out('\n');
    }

    /**
     * Simple shortcut to System.out.println(char).
     * 
     * @param chr A character to println on the standard output.
     */
    public void println(final char chr) {
        System.out.println(chr);
    }

    /**
     * Adds all the objects present in the given array to the output string
     * builder in a sequence, without any separator. No newline character is
     * added at the end.
     * 
     * @param objectArray An array of objects given as arguments.
     */
    public void out(final Object... objectArray) {
        for (final Object object: objectArray) {
            this.outputBuilder.append(object);
        }
    }

    /**
     * Prints all the objects present in the given array to the standard output
     * stream in a sequence, without any separator. No newline character is
     * added at the end.
     * 
     * @param objectArray An array of objects given as arguments.
     */
    public void print(final Object... objectArray) {
        for (final Object object: objectArray) {
            System.out.print(object);
        }
    }

    /**
     * Calls {@code out(objectArray)} then {@code out('\n')}.
     * 
     * @param objectArray An array of objects given as arguments.
     * @see               #out(Object...)
     * @see               #out(char)
     */
    public void outln(final Object... objectArray) {
        this.out(objectArray);
        this.out('\n');
    }

    /**
     * Calls {@code print(objectArray)} then adds a newline character.
     * 
     * @param objectArray An array of objects given as arguments.
     * @see               #print(Object...)
     */
    public void println(final Object... objectArray) {
        this.print(objectArray);
        System.out.println();
    }

    /**
     * Disables indentation just once. The next call to {@link #sameIndent()}
     * will result in adding nothing to the output. The following one however
     * will behave normally.
     */
    public void noIndentNext() {
        this.indentNext = false;
    }

    /**
     * Adds enough tabulations to reach the current indentation level.
     * Deactivated if {@link #noIndentNext()} was previously called, but then
     * allows indentation for the next parsed pattern.
     */
    public void sameIndent() {
        if (this.indentNext) {
            for (int i = 0; i < this.currentIndent; i++) {
                this.out('\t');
            }
        } else {
            this.indentNext = true;
        }
    }

    /**
     * Increments the current indentation level by one.
     */
    public void incIndent() {
        this.currentIndent += 1;
    }

    /**
     * Decrements the current indentation level by one.
     */
    public void decIndent() {
        this.currentIndent -= 1;
    }

    /**
     * Calls {@link #incIndent()} then {@link #sameIndent()}.
     */
    public void moreIndent() {
        this.incIndent();
        this.sameIndent();
    }

    /**
     * Calls {@link #decIndent()} then {@link #sameIndent()}.
     */
    public void lessIndent() {
        this.decIndent();
        this.sameIndent();
    }
}
