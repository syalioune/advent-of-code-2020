package fr.syalioune.puzzle.ferry;

import static fr.syalioune.puzzle.ferry.Direction.EAST;
import static fr.syalioune.puzzle.ferry.Direction.FORWARD;
import static fr.syalioune.puzzle.ferry.Direction.LEFT;
import static fr.syalioune.puzzle.ferry.Direction.NORTH;
import static fr.syalioune.puzzle.ferry.Direction.RIGHT;
import static fr.syalioune.puzzle.ferry.Direction.SOUTH;
import static fr.syalioune.puzzle.ferry.Direction.WEST;

import java.util.HashMap;
import java.util.Map;

public class Ferry implements Movable {


  private Map<Direction, Integer> positions = new HashMap<>();

  private Waypoint waypoint;

  public Ferry() {
    waypoint = new Waypoint();
  }

  public void move(String instruction) {
    if(instruction == null || !VALID_PATTERN.test(instruction)) {
      throw new IllegalArgumentException();
    }
    Integer distance = Integer.parseInt(instruction.substring(1));
    Direction instructionDirection = Direction.fromString(instruction);
    if(CARDINAL_DIRECTION.contains(instructionDirection) || instructionDirection == RIGHT || instructionDirection == LEFT) {
      waypoint.move(instruction);
    } else if(FORWARD == instructionDirection) {
      CARDINAL_DIRECTION.forEach(cardinal -> {
        positions.merge(cardinal, distance * waypoint.get(cardinal), (a, b) -> a + b);
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

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    positions.forEach((key, value) -> {
      sb.append(key.name());
      sb.append("=");
      sb.append(value);
      sb.append("\n");
    });
    return sb.toString();
  }

  public Waypoint getWaypoint() {
    return waypoint;
  }
}
