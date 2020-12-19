package fr.syalioune.puzzle.ferry;

import static fr.syalioune.puzzle.ferry.Direction.EAST;
import static fr.syalioune.puzzle.ferry.Direction.NORTH;
import static fr.syalioune.puzzle.ferry.Direction.SOUTH;
import static fr.syalioune.puzzle.ferry.Direction.WEST;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public interface Movable {

  Predicate<String> VALID_PATTERN = Pattern.compile("[N|S|E|W|F|L|R]\\d+").asMatchPredicate();

  List<Direction> CARDINAL_DIRECTION = Arrays.asList(NORTH, EAST, SOUTH, WEST);

  void move(String instruction);

  default int getSouth() {
    return 0;
  }

  default int getEast() {
    return 0;
  }

  default int getWest() {
    return 0;
  }

  default int getNorth() {
    return 0;
  }

}
