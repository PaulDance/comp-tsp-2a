package codegen;

/** Add Runtime MIPS : _system.out.println(), _new_object() */
public class LinkRuntime {
	public LinkRuntime(MIPS mips) {
		mips.println("### RUNTIME MIPS ###");
		
		// Object.equals(Object)
		mips.label("equals");
		mips.com("méthode Object.equals(Object)");
		mips.inst("seq $v0, $a0, $a1");
		mips.inst("jr   $ra");
		
		// Println
		mips.label("_system_out_println");
		mips.com("IN  $a0 = integer to print");
		mips.com("print integer");
		mips.inst("li   $v0,  1");
		mips.inst("syscall ");
		mips.com("print char newline");
		mips.inst("li   $a0, 10");
		mips.inst("li   $v0, 11");
		mips.inst("syscall");
		mips.com("end");
		mips.inst("jr   $ra");
		
		// Malloc
		mips.label("_new_object");
		mips.com("IN  $a0 = number of bytes");
		mips.com("OUT $v0 = allocated address");
		mips.com("malloc (sbrk)");
		mips.inst("li   $v0, 9");
		mips.inst("syscall");
		mips.com("initialize with zeros");
		mips.inst("move $t0, $a0");
		mips.inst("move $t1, $v0");
		mips.com("do until $t0=0");
		mips.label("_newobj_loop");
		mips.inst("beq  $t0, $zero, _newobj_exit");
		mips.inst("sb   $zero, 0($t1)");
		mips.inst("addi $t1, $t1,  1 ");
		mips.inst("addi $t0, $t0, -1 ");
		mips.inst("j    _newobj_loop ");
		mips.label("_newobj_exit");
		mips.com("done");
		mips.com("end");
		mips.inst("jr   $ra");
		
		// Array index out of bounds exception
		mips.label("_array_index_out_of_bounds_exc");
		mips.inst(".data");
		mips.label("_err_msg");
		mips.inst(".asciiz \"OutOfBoundsException\"");
		mips.inst(".text");
		mips.inst("la   $a0, _err_msg");
		mips.load("$v0", 4);
		mips.inst("syscall");
		mips.load("$v0", 10);
		mips.inst("syscall");
	}
}
