package codegen;

/** Add Runtime MIPS : _system.out.println(), _new_object() */
public class LinkRuntime {
    public LinkRuntime(final MIPS mips) {
        mips.println("### RUNTIME MIPS ###");

        // Object.equals(Object)
        mips.label("equals");
        mips.com("Object.equals(Object) method");
        mips.inst("seq  $v0, $a0, $a1");
        mips.retour();

        // Println
        mips.label("_system_out_println");
        mips.com("IN  $a0 = integer to print");
        mips.com("print integer");
        mips.load("$v0", 1);
        mips.inst("syscall");
        mips.com("print newline character");
        mips.load("$a0", 10);
        mips.load("$v0", 11);
        mips.inst("syscall");
        mips.com("end");
        mips.retour();

        // Malloc
        mips.label("_new_object");
        mips.com("Allocates a given number of bytes in the heap memory.");
        mips.com("IN  $a0 = number of bytes");
        mips.com("OUT $v0 = allocated address");
        mips.com("malloc (sbrk)");
        mips.load("$v0", 9);
        mips.inst("syscall");
        mips.com("initialize with zeros");
        mips.move("$t0", "$a0");
        mips.move("$t1", "$v0");
        mips.com("do until $t0 = 0");
        mips.label("_newobj_loop");
        mips.jump("_newobj_exit", "$t0");
        mips.inst("sb   $zero, 0($t1)");
        mips.add("$t1", 1);
        mips.add("$t0", -1);
        mips.jump("_newobj_loop");
        mips.label("_newobj_exit");
        mips.com("done");
        mips.com("end");
        mips.retour();

        // Array index out of bounds exception
        mips.label("_array_index_out_of_bounds_exc");
        mips.com("Thrown when an index is out of bounds while reading or writing to an array.");
        mips.inst(".data");
        mips.label("_aioobe_err_msg");
        mips.inst(".asciiz \"ArrayIndexOutOfBoundsException\"");
        mips.inst(".text");
        mips.inst("la   $a0, _aioobe_err_msg");
        mips.jump("_print_exc_str_and_sysexit");

        // New array negative size exception
        mips.label("_neg_array_size_exc");
        mips.com("Thrown when a new array declaration states a strictly negative size.");
        mips.inst(".data");
        mips.label("_nase_err_msg");
        mips.inst(".asciiz \"NegativeArraySizeException\"");
        mips.inst(".text");
        mips.inst("la   $a0, _nase_err_msg");
        mips.jump("_print_exc_str_and_sysexit");

        // Print exception messages common part
        mips.label("_print_exc_common_part");
        mips.inst(".data");
        mips.label("_err_msg_hd");
        mips.inst(".asciiz \"Minijava runtime exception: \"");
        mips.inst(".text");
        mips.inst("la   $a0, _err_msg_hd");
        mips.com("call to print_string");
        mips.load("$v0", 4);
        mips.inst("syscall");
        mips.retour();

        // Print exception message and system exit
        mips.label("_print_exc_str_and_sysexit");
        mips.com("Prints the common part, then the exception message and finally sysexits.");
        mips.move("$s0", "$a0");
        mips.jumpAdr("_print_exc_common_part");
        mips.move("$a0", "$s0");
        mips.com("call to print_string");
        mips.load("$v0", 4);
        mips.inst("syscall");
        mips.com("sysexit with error code");
        mips.load("$v0", 10);
        mips.inst("syscall");
    }
}
