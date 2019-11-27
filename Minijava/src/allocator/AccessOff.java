package allocator;

public class AccessOff implements Access {
	private static final String LW = "lw   ";
	private final String register; // "$fp" for frame, "$a0" for this , $gp, $sp
	private final Integer offset;
	
	public AccessOff(String register, Integer offset) {
		this.register = register;
		this.offset = offset;
	}
	
	@Override
	public String load(String register) {
		return LW + register + ", " + this.offset + "(" + this.register + ")";
	}
	
	@Override
	public String store(String register) {
		return "sw   " + register + ", " + this.offset + "(" + this.register + ")";
	}
	
	@Override
	public String loadSaved(String register) {
		if (this.register.equals("$a0")) {
			return LW + register + ", 0($sp)\n\t" + LW + register + ", " + this.offset + "(" + register + ")";
		}
		else {
			return this.load(register);
		}
	}
	
	@Override
	public String getRegister() {
		return null;
	}
	
	@Override
	public String toString() {
		return this.offset + "(" + this.register + ")";
	}
}
