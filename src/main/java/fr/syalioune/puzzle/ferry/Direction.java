package fr.syalioune.puzzle.ferry;

public enum Direction {

  NORTH("N"),
  EAST("E"),
  SOUTH("S"),
  WEST("W"),
  FORWARD("F"),
  LEFT("L"),
  RIGHT("R");

  private String directionString;

  Direction(String s) {
    directionString = s;
  }

  public static Direction fromString(String s) {
    Direction result = null;
    for (Direction direction : Direction.values()) {
      if (s.startsWith(direction.directionString)) {
        result = direction;
        break;
      }
    }
    return result;
  }
}
