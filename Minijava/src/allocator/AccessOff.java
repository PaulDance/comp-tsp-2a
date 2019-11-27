package allocator;

public class AccessOff implements Access {
  private static final String LW = "lw   ";

  private final String register; // "$fp" for frame, "$a0" for this , $gp, $sp ...
  private final Integer offset;

  public AccessOff(String register, Integer offset) {
    this.register = register;
    this.offset = offset;
  }

  public String load(String register) {
    return LW + register + ", " + offset + "(" + this.register + ")";
  }

  public String store(String register) {
    return "sw   " + register + ", " + offset + "(" + this.register + ")";
  }

  public String loadSaved(String register) {
    if (this.register.equals("$a0"))
      return LW + register + ", 0($sp)\n\t" + LW + register + ", " + offset + "(" + register + ")";
    else
      return load(register);
  }

  public String getRegister() {
    return null;
  }

  public String toString() {
    return offset + "(" + this.register + ")";
  }
}
