package semantic.symtab;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import main.IndentWriter;

/* Table des Symbole.
 * <ul><li>Implémentation plus large que nécéssaire pour Java ou MiniJava
 * <li>Implémentation générale d'un arbre de portées avec lookup recursif
 * <li>3 espaces de nommage séparés : Classe, Methode, Variable
 * <li>Utilisable pour intégrer l'héritage des classes Java dans l'arbre de portée
 * <li>.....
 * </ul>
 */
public class Scope {
  // Arbre Botom-up
  private Scope parent;
  // Tables de Symboles locales
  private final Table<String,InfoVar> variables; 
  private final Table<String,InfoMethod> methods;
  private final Table<String,InfoKlass> klasses; 
  /* En pratique avec Minijava :
   *  - klasses uniquement dans le scope Racine
   *  - methodes uniquement dans les scopes de classe
   *  - varibles partout sauf dans le scope Racine
   */

  // Debug and Printing
  private final String name;        
  private final List<Scope> scopes; // Arbre top-down
    
  // Constructors
  public Scope(Scope parent) { // default naming
    this(parent, (parent == null) ? "Root" : 
            parent.name + "_" + parent.scopes.size()
        );
  }

  public Scope(Scope parent, String name) {
    this.name = name;
    this.parent=parent;
    this.variables=new SimpleTable<>();  
    this.methods=new SimpleTable<>();  
    this.klasses=new SimpleTable<>();   
    this.scopes = new ArrayList<>();
    if (parent !=null){
      parent.scopes.add(this);
    }
 }
 
 /* Symtab Interface */
 // Variables
  public InfoVar lookupVariable(String name) {
    InfoVar v=null;
    for (Scope s=this; (s!=null) && (v==null); s=s.parent)
      v = s.variables.lookup(name);
    return v;
  }
  public InfoVar insertVariable(InfoVar v) { return variables.insert(v.getName(), v);}
  public Collection<InfoVar> getVariables() { return variables.getInfos(); }

  public Collection<InfoVar> getAllVariables() { 
    // variables de la portée courrante et des sous-protée
    // utile pour debug, check unused, utilisation de variables de bloc 
    List<InfoVar> res = new ArrayList<>();
    res.addAll(variables.getInfos());
    for (Scope s : this.scopes)
      res.addAll(s.getAllVariables());
    return res; }

  // Methods
  public InfoMethod lookupMethod(String name) {
    InfoMethod m=null;
    for (Scope s=this; (s!=null) && (m==null); s=s.parent)
      m = s.methods.lookup(name);
    return m;
  }
  public InfoMethod insertMethod(InfoMethod m) { return methods.insert(m.getName(), m); }
  public Collection<InfoMethod> getMethods() { return methods.getInfos(); }
 
  // Not Used : requis pour gestion du polymorphisme/surcharge
  // utile aussi pour indication overriding 
  public List<InfoMethod> lookupAllMethod(String name) {
    List<InfoMethod> list=new ArrayList<>();
    for (Scope s=this; (s!=null); s=s.parent) {
     InfoMethod m = s.methods.lookup(name);
     if (m != null) list.add(m);
    }
    return list;
  }

  // Klasses
  public InfoKlass lookupKlass(String name) {
    InfoKlass kl=null;
    for (Scope s=this; (s!=null) && (kl==null); s=s.parent)
      kl = s.klasses.lookup(name);
    return kl;
  }
  public InfoKlass insertKlass(InfoKlass kl) { return klasses.insert(kl.getName(), kl); }
  public Collection<InfoKlass> getKlasses() { return klasses.getInfos(); }

  /** Reconstruction en Passe2 : intégration de la hierarchie des classes dans 
   * l'arbre des portées.
   * <ul><li>Seul les fils de la racine peuvent muter dans un nouvelle arbre.
   * <li>Not safe : loop not checked here, field parent not final
   * <li>retourne true, si nodification effectuée
   * </ul>
   */
  public boolean mute (Scope oldParent, Scope newParent) {
    // mute only a level 1 scope (parent is a rootScope)
    if ((parent == null) || (parent.parent!=null)) return false;
    // don't create a new root
    if (newParent ==null) return false;
   // mute only a known parent
    if (parent != oldParent) return false;
    // OK
    parent=newParent;
    parent.scopes.add(this);
    oldParent.scopes.remove(this);
    return true;
    }
  
  // Impressions
  /** Impression Noeud */
  @Override 
  public String toString() {
    return "Scope " + name;  
  }

  /** Impression Arbre bottom-up  */
  public String toPrintUp() {
    return name + " -> " + parent.toPrintUp();  
  }  
  
  /** Impression Arbre top-down avec symboles */
  public String toPrint() {
    IndentWriter out =new IndentWriter();
    print(out);
    return out.toString();
  }
  
  private void print(IndentWriter out) {
    out.println("Scope " + name);
    out.indent();
    for (Info i : getKlasses())   out.println(i);
    for (Info i : getMethods())   out.println(i);
    for (Info i : getVariables()) out.println(i);
    for (Scope s : scopes) s.print(out);
    out.outdent();
  }
}
