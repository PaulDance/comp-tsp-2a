package allocator;

public interface Access {
  String store(String register); // store register to Variable
  String load(String register); // Load Variable in register
  String loadSaved(String register); // use saved a0, a1 a2 a3 in stack for setting args
  String getRegister(); // try to optimize temporary register if variable already in a register
}
