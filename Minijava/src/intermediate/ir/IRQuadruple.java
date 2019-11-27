package intermediate.ir;

import main.OPER;

/** Classe abstraite mère des Instructions Intermediaires
 *<p><b>IRQuadruple</b> = Code à 3 adresses </b> :
 *<br> { OPER op, IRvar arg1, IRVar arg2, IRVar result } 
 *<br> Interpretation commune : result = op ( arg1, arg2 )
 */
public abstract class IRQuadruple {
  /** Debug. */
  public abstract String toString(); 

  /** Opération */
  public final OPER op;
  /** "Adresse 1" */
  public final IRVar arg1;
  /** "Adresse 2" */
  public final IRVar arg2;
  /** "Adresse 3" */
  public final IRVar result;

  IRQuadruple(OPER op, IRVar arg1, IRVar arg2, IRVar result) {
    this.op = op;
    this.arg1 = arg1;
    this.arg2 = arg2;
    this.result = result;
  }

}
