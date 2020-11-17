package allocator;

public class AccessReg implements Access {
    private static final String LW = "lw   ";
    private final String register;

    public AccessReg(String register) {
        this.register = register;
    }

    @Override
    public String store(String register) {
        if (!register.equals(this.register)) {
            return "move " + this.register + ", " + register;
        } else {
            return null;
        }
    }

    @Override
    public String load(String register) {
        if (!register.equals(this.register)) {
            return "move " + register + ", " + this.register;
        } else {
            return null;
        }
    }

    @Override
    public String loadSaved(String register) {
        switch (this.register) {
            case "$a0":
                return LW + register + ", 0($sp)";

            case "$a1":
                return LW + register + ", 4($sp)";

            case "$a2":
                return LW + register + ", 8($sp)";

            case "$a3":
                return LW + register + ", 12($sp)";

            default:
                return this.load(register);
        }
    }

    @Override
    public String getRegister() {
        return this.register;
    }

    @Override
    public String toString() {
        return this.register;
    }
}
