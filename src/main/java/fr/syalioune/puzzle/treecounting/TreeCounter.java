package fr.syalioune.puzzle.treecounting;

/**
 * Tree counting logic.
 */
public class TreeCounter {

  /**
   * Return the new of tree in the map from the top left given a slope.
   *
   * @param map
   *          The map for tree searching
   *
   * @param slope
   *          The slove for moving
   */
  public long count(char[][] map, Slope slope) {
    long result = 0;
    if(map == null || map.length <= slope.getDown() || map[0].length <= slope.getRight()) {
      throw new IllegalArgumentException();
    }
    int x = 0;
    int y = 0;
    boolean insideMap = true;
    while(insideMap) {
      x += slope.getDown();
      y = (y + slope.getRight()) % map[0].length;
      insideMap = x < map.length;
      if(insideMap && map[x][y] == '#') result++;
    }
    return result;
  }
}
