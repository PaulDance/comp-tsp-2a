package semantic.symtab;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/** Table de symbole élémentaitre en utilisant HashMap */
public class SimpleTable<R extends Info> implements Table<String,R> {

  private Map<String, R> symbols;

  public SimpleTable() {
    this.symbols = new HashMap<>();
  }

  @Override
  public R lookup(String name) {
    return symbols.get(name);
  }
  
  @Override
  public R insert(String name, R info) {
    return symbols.put(name, info);
  }

  @Override
  public Collection<R> getInfos() {
    return symbols.values();
  }
  
  @Override
  public String toString() {
    return "SimpleTable [" + symbols + "]";
  }

 
}
