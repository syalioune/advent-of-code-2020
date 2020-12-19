package fr.syalioune.puzzle.ferry;

import static fr.syalioune.puzzle.ferry.Direction.EAST;
import static fr.syalioune.puzzle.ferry.Direction.LEFT;
import static fr.syalioune.puzzle.ferry.Direction.NORTH;
import static fr.syalioune.puzzle.ferry.Direction.RIGHT;
import static fr.syalioune.puzzle.ferry.Direction.SOUTH;
import static fr.syalioune.puzzle.ferry.Direction.WEST;

import java.util.HashMap;
import java.util.Map;

public class Waypoint implements Movable {

  private Map<Direction, Integer> positions = new HashMap<>();


  public Waypoint() {
    positions.put(EAST, 10);
    positions.put(NORTH, 1);
  }

  @Override
  public void move(String instruction) {
    if(instruction == null || !VALID_PATTERN.test(instruction)) {
      throw new IllegalArgumentException();
    }
    Integer distance = Integer.parseInt(instruction.substring(1));
    Direction instructionDirection = Direction.fromString(instruction);
    if(CARDINAL_DIRECTION.contains(instructionDirection)) {
      positions.merge(instructionDirection, distance, (a, b) -> a + b);
    } else if (instructionDirection == RIGHT) {
      Map<Direction, Integer> oldPositions = positions;
      positions = new HashMap<>();
      oldPositions.forEach((dir, value) -> {
        int index = (dir.ordinal() + (distance / 90)) % 4 ;
        positions.put(Direction.values()[index], value);
      });
    } else if( instructionDirection == LEFT) {
      Map<Direction, Integer> oldPositions = positions;
      positions = new HashMap<>();
      oldPositions.forEach((dir, value) -> {
        int index = (4 + (dir.ordinal() - ((distance / 90) % 4))) % 4 ;
        positions.put(Direction.values()[index], value);
      });
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

  public int get(Direction direction) {
    return positions.getOrDefault(direction, 0);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("direction=");
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
