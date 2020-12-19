package fr.syalioune.puzzle.ferry;

import static fr.syalioune.puzzle.ferry.Direction.EAST;
import static fr.syalioune.puzzle.ferry.Direction.FORWARD;
import static fr.syalioune.puzzle.ferry.Direction.LEFT;
import static fr.syalioune.puzzle.ferry.Direction.NORTH;
import static fr.syalioune.puzzle.ferry.Direction.RIGHT;
import static fr.syalioune.puzzle.ferry.Direction.SOUTH;
import static fr.syalioune.puzzle.ferry.Direction.WEST;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class Ferry {

  private Direction direction;

  private Map<Direction, Integer> positions = new HashMap<>();

  public static final Predicate<String> VALID_PATTERN = Pattern.compile("[N|S|E|W|F|L|R]\\d+").asMatchPredicate();

  public Ferry() {
    direction = EAST;
  }
  public Ferry(Direction initialDirection) {
    direction = initialDirection;
  }

  public void move(String instruction) {
    if(instruction == null || !VALID_PATTERN.test(instruction)) {
      throw new IllegalArgumentException();
    }
    List<Direction> cardinalDirection = Arrays.asList(NORTH, EAST, SOUTH, WEST);
    Integer distance = Integer.parseInt(instruction.substring(1));
    Direction instructionDirection = Direction.fromString(instruction);
    if(cardinalDirection.contains(instructionDirection)) {
      positions.merge(Direction.fromString(instruction), distance, (a, b) -> a + b);
    } else if (instructionDirection == FORWARD) {
      positions.merge(direction, distance, (a, b) -> a + b);
    } else if (instructionDirection == RIGHT) {
      int index = (direction.ordinal() + (distance / 90)) % 4 ;
      direction = Direction.values()[index];
    } else if( instructionDirection == LEFT) {
      int index = (4 + (direction.ordinal() - ((distance / 90) % 4))) % 4 ;
      direction = Direction.values()[index];
    }
  }

  public int getNorth() {
    return positions.getOrDefault(NORTH, 0);
  }

  public int getSouth() {
    return positions.getOrDefault(SOUTH, 0);
  }

  public int getEast() {
    return positions.getOrDefault(EAST, 0);
  }

  public int getWest() {
    return positions.getOrDefault(WEST, 0);
  }

  public Direction getDirection() {
    return direction;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("direction=");
    sb.append(direction.name());
    sb.append("\n");
    positions.forEach((key, value) -> {
      sb.append(key.name());
      sb.append("=");
      sb.append(value);
      sb.append("\n");
    });
    return sb.toString();
  }
}
