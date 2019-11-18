
/**
 * Utilities meant to help pretty-print the parsed JSON
 * value. The result is displayed indented using tabulations.
 * 
 * In order to do that, each parsed pattern handles its
 * own indentation using `sameIndent()` and can modify the
 * global indentation level with `incIndent()` or `decIndent()`.
 * Other methods are implemented here in order to reduce the
 * code quantity necessary in the pattern recognition below.
 */
public class JsonPrettyPrintingUtils {
	/**
	 * Stores the current level of indentation as a number
	 * of tabulation characters.
	 */
	private long currentIndent = 0;
	/**
	 * Indicates whether the next parsed pattern will be
	 * allowed to be indented or not.
	 */
	private boolean indentNext = true;
	/**
	 * The `StringBuilder` used to store the pretty-print
	 * result progressively in order to display it only
	 * when the parsed input is syntaxically correct.
	 */
	private StringBuilder outputBuilder = new StringBuilder();
	
	/**
	 * Returns the string meant to be the output.
	 */
	public String getOutput() {
		return this.outputBuilder.toString();
	}
	
	/**
	 * Appends the given character to the output builder.
	 */
	public void out(char chr) {
		this.outputBuilder.append(chr);
	}
	
	/**
	 * Simple shortcut to System.out.print(char).
	 */
	public void print(char chr) {
		System.out.print(chr);
	}
	
	/**
	 * Calls `out(chr)` then `out('\n')`.
	 */
	public void outln(char chr) {
		this.out(chr);
		this.out('\n');
	}
	
	/**
	 * Simple shortcut to System.out.println(char).
	 */
	public void println(char chr) {
		System.out.println(chr);
	}
	
	/**
	 * Adds all the objects present in the given array to
	 * the output string builder in a sequence, without any
	 * separator. No newline character is added at the end.
	 */
	public void out(Object... objectArray) {
		for (Object object: objectArray) {
			this.outputBuilder.append(object);
		}
	}
	
	/**
	 * Prints all the objects present in the given array to
	 * the standard output stream in a sequence, without any
	 * separator. No newline character is added at the end.
	 */
	public void print(Object... objectArray) {
		for (Object object: objectArray) {
			System.out.print(object);
		}
	}
	
	/**
	 * Calls `out(objectArray)` then `out('\n')`.
	 */
	public void outln(Object... objectArray) {
		this.out(objectArray);
		this.out('\n');
	}
	
	/**
	 * Calls `print(objectArray)` then adds a newline character.
	 */
	public void println(Object... objectArray) {
		this.print(objectArray);
		System.out.println();
	}
	
	/**
	 * Disables identation just once.
	 */
	public void noIndentNext() {
		this.indentNext = false;
	}
	
	/**
	 * Prints enough tabulations to reach the current
	 * indentation level. Deactivated if `noIndentNext`
	 * was previously called, but then allows indentation
	 * for the next parsed pattern.
	 */
	public void sameIndent() {
		if (this.indentNext) {
			for (int i = 0; i < this.currentIndent; i++) {
				this.out('\t');
			}
		}
		else {
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
	 * Calls `incIndent()` then `sameIndent()`.
	 */
	public void moreIndent() {
		this.incIndent();
		this.sameIndent();
	}
	
	/**
	 * Calls `decIndent()` then `sameIndent()`.
	 */
	public void lessIndent() {
		this.decIndent();
		this.sameIndent();
	}
}
