package allocator;

public class AccessConst implements Access {
    private final int immediate;

    public AccessConst(int immediate) {
        this.immediate = immediate;
    }

    @Override
    public String store(String register) {
        throw new main.CompilerException("genMIPS : store in immediate !?!?");
    }

    @Override
    public String load(String register) {
        return "li   " + register + ", " + this.immediate;
    }

    @Override
    public String loadSaved(String register) {
        return this.load(register);
    }

    @Override
    public String getRegister() {
        return null;
    }

    @Override
    public String toString() {
        return "" + this.immediate;
    }
}
