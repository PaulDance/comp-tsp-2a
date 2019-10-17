import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Set;

/**
 * "Extension" de la classe ASTNode pour differentes impressions d'arbre Utilise
 * ASTNode.*.toString() pour label des noeuds Methode statique print(mode=0..11) Main() pour
 * test/demo
 * 
 * @author hennequi Mars 2017
 */
public class ASTPrint {
  // stringBuilder pour construction texte imprimable
  private static StringBuilder sb;

  // jeux de caractere pour impression Unicode (char graphique Box Drawing
  private enum BOX {
    H('\u2500') /* ─ */, V('\u2502') /* │ */, HV('\u253c') /* ┼ */, 
    RV('\u251c') /* ├ */, HU('\u2534') /* ┴ */, HD('\u252c') /* ┬ */, 
    RD('\u250c') /* ┌ */, LD('\u2510') /* ┐ */, RU('\u2514') /* └ */,
    LU('\u2518') /* ┘ */;
    private final Character c;

    BOX(char c) { this.c = c; }

    @Override
    public String toString() { return c.toString(); }

    public char toChar() { return c; }
  }

  // interface acces aux N variantes
  public static String print(ASTNode root, int mode) {
    sb = new StringBuilder();
    switch (mode) {
      case 0: print0(root); break;
      case 1: print1(root); break;
      case 2: print2(root); break;
      case 3: print3(root); break;
      case 4: print4(root); break;
      case 5: print5(root, gr[0], false); break;
      case 6: print5(root, gr[1], true); break;
      case 7: print5(root, gr[2], false); break;
      case 8: print5(root, gr[3], true); break;
      default: new HPrint(root, mode - 9); break;
    }
    return (sb.toString());
  }

  // Main for testing
  private static AST n(String name, ASTNode... fils) {
    return new AST(name, fils);
  }

  public static void main(String[] args) {
    AST[] arbres = {
            n("*", n("+", n("2"), n("3")), n("*", n("4"), n("5"))),
            n("RRR", n("R*", n("R+", n("4"), n("+"), n("RlabelLong", n("222"),
                n("*"), n("3"))), n("*"), n("R*", n("2"), n("*"), n("1"))))
                };
    for (ASTNode n : arbres)
      for (int i = 0; i < 15; i++)
        System.out.println("print" + i + "\n" + print(n, i));
  }
  
  // helper
  private static boolean isLeaf(ASTNode n) {
    return ! n.iterator().hasNext();
  }

  // /// Impressions Monoligne
  // Prefixe, parenthese "lisp"
  private static void print0(ASTNode node) {
    if (node == null) return;
    if ( ! isLeaf(node) ) sb.append("(");
    sb.append(node);
    for (ASTNode n : node) {
      sb.append(" ");
      print0(n);
    }
    if ( ! isLeaf(node) ) sb.append(")");
  }

  // Prefixe, parenthese "fonctionnelle
  private static void print1(ASTNode node) {
    if (node == null) return;
    sb.append(node);
    if (isLeaf(node)) return;
    sb.append("(");
    boolean first = true;
    for (ASTNode n : node) {
      if (first) first = false;
      else sb.append(", ");
      print1(n);
    }
    sb.append(")");
  }

  // Postfixe, parenthese "lisp"
  private static void print2(ASTNode node) {
    if (node == null) return;
    if (isLeaf(node)) {
      sb.append(node);
      return;
    }
    sb.append("(");
    for (ASTNode n : node) {
      sb.append(" ");
      print2(n);
    }
    sb.append(" ").append(node).append(" )");
  }

  // Infixe
  private static void print3(ASTNode node) {
    if (node == null) return;
    if (isLeaf(node)) {
      sb.append(node);
      return;
    }
    sb.append("(");
    boolean first = true;
    for (ASTNode n : node) {
      print3(n);
      if (first) {
        first = false;
        sb.append(' ').append(node).append(' ');
      }
    }
    sb.append(")");
  }

  // Leaf_Only
  private static void print4(ASTNode root) { // leaf_only
    if (root == null) return;
    if (isLeaf(root)) sb.append(" ").append(root);
    for (ASTNode n : root)
      print4(n);
  }

  // Impressions "Verticales" ( ASCII / UNICODE )
  private static final String[][] gr = { {"|-", "| ", "\\-", "  "}, // ASCII
      {"|--", "|  ", "\\--", "   "}, // ASCII Large
      {"\u251c", "\u2502", "\u2514", " "}, // Unicode
      {"\u251c\u2500", "\u2502 ", "\u2514\u2500", "  "} // Unicode Large
      };
  private static String[] graph;
  private static boolean aere;

  private static void print5(ASTNode node, String[] charset, boolean largeVert) {
    aere = largeVert;
    graph = charset;
    print5(node, "");
  }

  private static void print5(ASTNode node, String indent) {
    if (node == null) return;
    sb.append(node);
    for (Iterator<ASTNode> it = node.iterator() ; it.hasNext() ; ) {
      ASTNode f = it.next();
      if (aere)
        sb.append(System.lineSeparator()).append(indent).append(graph[1]);
      sb.append(System.lineSeparator()).append(indent);
      if (it.hasNext()) {
        sb.append(graph[0]);
        print5(f, indent + graph[1]);
      } else {
        sb.append(graph[2]);
        print5(f, indent + graph[3]);
      }
    }
  }

  // Impressions Horizontales (ASCII/UNICODE)
  static class HPrint {
    private static final int HTAB = 1; // espacement min entre noeud
    private static final int HINDENT = 0; // indentation initiale
    private static final int HTRUNC = 256; // trunc line
    private int indent; // variable "globale" pour recursion recursePosition()

    // mode d'impression
    private boolean reverse; // inversion haut/bas de l'impression
    private boolean unicode; // impression avec Unicode "Box Drawings"
    private boolean unicodeLarge; // aeration verticale
    private boolean byHauteur; // impreession par hauteur vs profondeur

    private Map<Integer, String> labels;
    private Map<Integer, List<Integer>> connectors;
    private Set<Integer> posFils;

    private class Position { // Info de positionnement de chaque noeud
      int hauteur; // hauteur dans l'arbre
      int profondeur; // profondeur dans l'arbre
      int largeur; // largeur impression du sous arbre
      int offset; // decalage sous arbre si label > largeut(sous-arbre)
      int hpos; // position horizontale du noeud
    }

    private Map<ASTNode, Position> pos;

    private char[] charLine;

    HPrint(ASTNode root, int mode) {
      if (root == null) return;
      this.pos = new HashMap<>();
      this.indent = HINDENT;
      recurse1(root, 0);
      recurse2(root);
      this.labels = new HashMap<>(); // poosition des labels d'un niveau
      this.connectors = new HashMap<>(); // position des branches d'un nivwau
      this.posFils = new HashSet<>(); // position des branches "héritées"
      sb.append("\n");
      this.reverse = false;
      this.unicode = false;
      this.unicodeLarge = false;
      this.byHauteur = false;

      this.charLine = new char[HTRUNC + 1];
      this.initCharLine();
      switch (mode) {
        case 0: printProf(root); break;
        case 1: unicode = true; printByLevel(root); break;
        case 2: byHauteur = true; printByLevel(root); break;
        case 3: unicode = true; byHauteur = true; printByLevel(root);break;
        case 4: reverse = true; unicode = true; byHauteur = true; printByLevel(root); break;
        case 5: unicodeLarge = true; reverse = true; unicode = true; byHauteur = true; printByLevel(root); break;
        default: sb.append("Mode Impression Inconnu");
      }
    }

    private void recurse1(ASTNode node, int level) { // calcul des map position sauf hpos
      Position p = new Position();
      pos.put(node, p);
      p.profondeur = level;
      int labelSize = node.toString().length() + HTAB;
      if (isLeaf(node)) { // feuille
        p.hauteur = 0;
        p.offset = 0;
        p.largeur = labelSize;
      } else {
        int larg = 0;
        int haut = 0;
        for (ASTNode n : node) {
          recurse1(n, level + 1);
          larg = larg + pos.get(n).largeur;
          haut = Math.max(haut, pos.get(n).hauteur);
        }
        p.hauteur = haut + 1;
        p.largeur = Math.max(labelSize, larg);
        p.offset = p.largeur - larg;
      }
    }

    private void recurse2(ASTNode node) { // calcul map hpos, avec indent=vatiable globale
      Position p = pos.get(node);
      p.hpos = indent + (p.largeur - HTAB) / 2;
      if (isLeaf(node))
        indent += p.largeur;
      else {
        indent += p.offset / 2;
        for (ASTNode n : node)
          recurse2(n);
        indent += (p.offset + 1) / 2;
      }
    }

    private void printProf(ASTNode root) { // parcours en largeur avec queue
      Queue<ASTNode> q = new LinkedList<>();
      q.add(root);
      while (!q.isEmpty()) {
        for (int Nodes = q.size(); Nodes > 0; Nodes--) {
          ASTNode node = q.remove();
          printNode(node);
          for (ASTNode n : node)
            q.add(n);
        }
        pushPrint();
      }
    }

    private void printByLevel(ASTNode root) {
      int haut = pos.get(root).hauteur;
      for (int level = 0; level <= haut; level++) {
        for (Map.Entry<ASTNode, Position> e : pos.entrySet()) {
          ASTNode n = e.getKey();
          Position p = e.getValue();
          if (byHauteur && p.hauteur == haut - level)
            printNode(n);
          if (!byHauteur && p.profondeur == level)
            printNode(n);
        }
        pushPrint();
      }
    }

    private void printNode(ASTNode node) { 
      // positionne les maps labels et connector pour 1 niveau de l'arbree
      int hp = pos.get(node).hpos;
      labels.put(hp - node.toString().length() / 2, node.toString());
      posFils.remove(hp);
      if ( isLeaf(node) ) return;
      List<Integer> list = new ArrayList<>();
      for (ASTNode n : node)
        list.add(pos.get(n).hpos);
      connectors.put(hp, list);
    }

    private void pushPrint() {
      for (Map.Entry<Integer, String> e : labels.entrySet()) {
        int i = e.getKey();
        String str = e.getValue();
        for (int j = 0; j < str.length(); j++)
          setCharLine(i + j, str.charAt(j));
      }
      printCharLine();
      labels.clear();

      if (connectors.isEmpty()) {
        if (reverse)
          sb.insert(0, '\n');
        return;
      }
      if (unicode && !unicodeLarge)
        connectorPrint();
      else {
        for (int pere : connectors.keySet())
          setCharLine(pere, pipe());
        printCharLine();
        connectorPrint();
        printCharLine();
      }
      connectors.clear();
    }

    private void connectorPrint() {
      for (Map.Entry<Integer, List<Integer>> e : connectors.entrySet()) {
        int pere = e.getKey();
        List<Integer> list = e.getValue();
        int start = list.get(0);
        int stop = list.get(list.size() - 1);
        for (int j = start; j <= stop; j++)
          setCharLine(j, unicode ? BOX.H.toChar() : '-');
        if (unicode)
          setCharLine(pere, (reverse ? BOX.HD : BOX.HU).toChar());
        for (Integer fils : list) {
          setFils(fils, pere, start, stop);
          posFils.add(fils);
        }
      }
      printCharLine();
    }

    private char pipe() {
      return unicode ? BOX.V.toChar() : '|';
    }

    private void setFils(int fils, int pere, int start, int stop) {
      if (start == stop) {
        setCharLine(fils, pipe());
        return;
      }
      if (!unicode) return;
      BOX br = reverse ? BOX.HU : BOX.HD;
      if (fils == start)
        br = reverse ? BOX.RU : BOX.RD;
      if (fils == stop)
        br = reverse ? BOX.LU : BOX.LD;
      if (fils == pere)
        br = BOX.HV;
      setCharLine(fils, br.toChar());
    }

    // impression d'une ligne avec trunc et memoire des branche(posFils)
    private void initCharLine() {
      for (int i = 0; i < HTRUNC; i++)
        setCharLine(i, ' ');
    }

    private void setCharLine(int index, char c) {
      if (index < HTRUNC)
        charLine[index] = c;
    }

    private void printCharLine() {
      StringBuilder s = new StringBuilder();
      int last = 0;
      for (int j = 0; j < HTRUNC; j++)
        if (charLine[j] != ' ') last = j;
      for (int j = 0; j <= last; j++)
        s.append(charLine[j]);
      s.append('\n');
      if (reverse) sb.insert(0, s);
      else sb.append(s);
      // reset charLine
      initCharLine();
      for (Integer i : posFils)
        setCharLine(i, pipe());
    }
  }
}

