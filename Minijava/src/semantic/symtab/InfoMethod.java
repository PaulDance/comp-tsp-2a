package semantic.symtab;

import java.util.Collection;
import java.util.List;

/** Déclaration de Methode pour la table de symboles.
 * <p> Insertion d'une Methode :
 * <ul><li> Ajout de la Methode : scope.add(new InfoMethod);
 * <li> Entrée dans un nouveau scope : scope=new Scope(scope);
 * <li> Ajout des arguments Formels: scope.add(new InfoVar); ...
 * <li> Entrée dans un nouveau scope : scope=new Scope(scope);
 * <li> mise a jour : InfoMethod.setScope(scope);
 * </ul>
 * <p> Le scope de la Méthode est fils du scope des Arguments 
 * qui est file du scope de déclaration de methode.
 * <p>NB : les arguments formels sont dupliqués comme une liste ordonée dans InfoMethod et
 * comme une collection non ordonée dans le scope des argument
 */
public class InfoMethod implements Info {
  private final String returnType;
  private final String name;
  private final InfoVar[] args; 
// NB: currentKlassName == Type de args[0] = "this"

  /** link to Method attributes : formal args , local variables */
  private Scope scope;
   
  // getters
  public String getReturnType() { return returnType; }
  public String getName() { return name; }
  public InfoVar[] getArgs() { return args; }
  public Scope getScope() { return scope; }

  // setters
  public void setScope(Scope sc) { this.scope=sc; }
  
  // helpers
  public Collection<InfoVar> getLocals() { return scope.getVariables(); }  
  // not used : getArgs=scope.getParent().getVariables)_
  // TO DO ? getAllLocals (recursif top-down) pour gestion des variables de block
    
  // constructors : varargs or List<>
  public InfoMethod( String returnType, String name, InfoVar... args) {
    this.returnType = returnType;
    this.name = name;
    this.args =args;
    this.scope=null;
 }
  
  public InfoMethod( String returnType, String name, List<InfoVar> args) {
    this.returnType = returnType;
    this.name = name;
    this.args = args.toArray(new InfoVar[0]);
    this.scope=null;
 }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(returnType).append(' ').append(name);
    sb.append('(');
    if (args.length != 0) sb.append(args[0]);
    for (int i=1; i<args.length; i++)
      sb.append(", ").append(args[i]);
    sb.append(')'); 
    return sb.toString();
  }
  
  // Not Used : Nom Unique pour polymorphisme/surcharge/redefinition
  public String getCanonicalName() { 
    StringBuilder sb = new StringBuilder();
    sb.append("__").append(name);
    for (InfoVar v : args)
      sb.append('_').append(v.getType());
    return sb.toString();
  }
}
