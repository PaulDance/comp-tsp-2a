package semantic.symtab;

import java.util.Collection;

/** Déclaration de classe pour la table de symboles.
 * <p> Insertion d'une classe :
 * <ul><li> Ajout de la classe : scope.add(new InfoKlass);
 * <li> Entrée dans un nouveau scope : scope=new Scope(scope);
 * <li> mise a jour : InfoKlass.setScope(scope);
 * </ul>
 * <p> Le scope de la classe est fils du scope de la déclaration de classe
 */
public class InfoKlass implements Info {
  private final String name;
  private final String parent;
  /** link to Klass attributes : fields, methods */
  private Scope scope;
  
  //getters
  public String getName()   { return name; }
  public String getParent() { return parent; }
  public Scope getScope()   { return this.scope; }
  
  // setter
  public void setScope(Scope scope) { this.scope = scope; }

  // helpers
  public Collection<InfoMethod> getMethods() { return scope.getMethods(); }
  public Collection<InfoVar> getFields()     { return scope.getVariables(); }

  // constructor
  public InfoKlass(String name, String parentName) {
    this.name = name;
    this.parent = parentName;
    this.scope = null;
  }

  // print
  @Override
  public String toString() {
    return "class " + name + " extends " + parent;
  }
}
