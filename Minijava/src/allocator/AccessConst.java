package allocator;

public class AccessConst implements Access {
  private final int immediate;

  public AccessConst(int immediate) {
    this.immediate = immediate;
  }

  public String store(String register) {
    throw new main.CompilerException("genMIPS : store in immediate !?!?");
  }

  public String load(String register) {
    return "li   " + register + ", " + immediate;
  }

  public String loadSaved(String register) {
    return load(register);
  }

  public String getRegister() {
    return null;
  }

  public String toString() {
    return "" + immediate;
  }
}
