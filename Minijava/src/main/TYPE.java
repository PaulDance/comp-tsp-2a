package main;

/** Enumeration des types primitifs et mapping toString() */
public enum TYPE {
  BOOL("boolean"), INT("int"), 
  INT_ARRAY("int[]"),
  UNDEF("undef");

  private final String name;

  private TYPE(String name) { this.name = name; }

  @Override
  public String toString() { return name; }
}
