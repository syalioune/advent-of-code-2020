package fr.syalioune.puzzle.treecounting;

/**
 * Tobbogan slope.
 */
public class Slope {

  private int right;

  private int down;

  public Slope(int right, int down) {
    this.right = right;
    this.down = down;
  }

  public int getRight() {
    return right;
  }

  public int getDown() {
    return down;
  }
}
