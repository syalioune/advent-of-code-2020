package fr.syalioune.puzzle.ferry;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class FerryNavigationalSystemShould {

  @ParameterizedTest
  @MethodSource("invalidInstructionSource")
  public void shouldRejectInvalidInput(String instruction) {
    // Arrange
    Ferry ferry = new Ferry();

    // Act
    Assertions.assertThrows(IllegalArgumentException.class, () -> ferry.move(instruction));

    // Assert
    // Nothing to be done
  }

  @ParameterizedTest
  @MethodSource("validCardinalDirectionSource")
  public void shouldMoveTheFerryInTheCorrectCardinalDirection(String instruction, int north, int south, int east, int west, Direction direction) {
    // Arrange
    Ferry ferry = new Ferry();

    // Act
    ferry.move(instruction);

    // Assert
    Assertions.assertEquals(north, ferry.getNorth());
    Assertions.assertEquals(south, ferry.getSouth());
    Assertions.assertEquals(east, ferry.getEast());
    Assertions.assertEquals(west, ferry.getWest());
    Assertions.assertEquals(direction, ferry.getDirection());
  }

  @ParameterizedTest
  @MethodSource("validForwardDirectionSource")
  public void shouldMoveTheFerryInTheCorrectForwardDirection(String instruction, Direction initialDirection, int north, int south, int east, int west) {
    // Arrange
    Ferry ferry = new Ferry(initialDirection);

    // Act
    ferry.move(instruction);

    // Assert
    Assertions.assertEquals(north, ferry.getNorth());
    Assertions.assertEquals(south, ferry.getSouth());
    Assertions.assertEquals(east, ferry.getEast());
    Assertions.assertEquals(west, ferry.getWest());
    Assertions.assertEquals(initialDirection, ferry.getDirection());
  }

  @ParameterizedTest
  @MethodSource("validRotateDirectionSource")
  public void shouldRotateTheFerryInTheCorrectDirection(String instruction, Direction initialDirection, Direction finalDirection) {
    // Arrange
    Ferry ferry = new Ferry(initialDirection);

    // Act
    ferry.move(instruction);

    // Assert
    Assertions.assertEquals(finalDirection, ferry.getDirection());
  }

  static Stream<String> invalidInstructionSource() {
    return Stream.of(
        null,
        "",
        " ",
        "Z",
        "NN"
    );
  }

  static Stream<Arguments> validCardinalDirectionSource() {
    return Stream.of(
        Arguments.arguments("N3",3,0,0,0,Direction.EAST),
        Arguments.arguments("S3",0,3,0,0,Direction.EAST),
        Arguments.arguments("E3",0,0,3,0,Direction.EAST),
        Arguments.arguments("W3",0,0,0,3,Direction.EAST)
    );
  }

  static Stream<Arguments> validForwardDirectionSource() {
    return Stream.of(
        Arguments.arguments("F3",Direction.NORTH,3,0,0,0),
        Arguments.arguments("F3",Direction.SOUTH,0,3,0,0),
        Arguments.arguments("F3",Direction.EAST,0,0,3,0),
        Arguments.arguments("F3",Direction.WEST,0,0,0,3)
    );
  }

  static Stream<Arguments> validRotateDirectionSource() {
    return Stream.of(
        Arguments.arguments("R90",Direction.EAST, Direction.SOUTH),
        Arguments.arguments("R180",Direction.EAST, Direction.WEST),
        Arguments.arguments("R270",Direction.EAST, Direction.NORTH),
        Arguments.arguments("R360",Direction.EAST, Direction.EAST),
        Arguments.arguments("R90",Direction.NORTH, Direction.EAST),
        Arguments.arguments("R180",Direction.NORTH, Direction.SOUTH),
        Arguments.arguments("R270",Direction.NORTH, Direction.WEST),
        Arguments.arguments("R360",Direction.NORTH, Direction.NORTH),
        Arguments.arguments("R90",Direction.SOUTH, Direction.WEST),
        Arguments.arguments("R180",Direction.SOUTH, Direction.NORTH),
        Arguments.arguments("R270",Direction.SOUTH, Direction.EAST),
        Arguments.arguments("R360",Direction.SOUTH, Direction.SOUTH),
        Arguments.arguments("R90",Direction.WEST, Direction.NORTH),
        Arguments.arguments("R180",Direction.WEST, Direction.EAST),
        Arguments.arguments("R270",Direction.WEST, Direction.SOUTH),
        Arguments.arguments("R360",Direction.WEST, Direction.WEST),
        Arguments.arguments("L90",Direction.EAST, Direction.NORTH),
        Arguments.arguments("L180",Direction.EAST, Direction.WEST),
        Arguments.arguments("L270",Direction.EAST, Direction.SOUTH),
        Arguments.arguments("L360",Direction.EAST, Direction.EAST),
        Arguments.arguments("L90",Direction.NORTH, Direction.WEST),
        Arguments.arguments("L180",Direction.NORTH, Direction.SOUTH),
        Arguments.arguments("L270",Direction.NORTH, Direction.EAST),
        Arguments.arguments("L360",Direction.NORTH, Direction.NORTH),
        Arguments.arguments("L90",Direction.SOUTH, Direction.EAST),
        Arguments.arguments("L180",Direction.SOUTH, Direction.NORTH),
        Arguments.arguments("L270",Direction.SOUTH, Direction.WEST),
        Arguments.arguments("L360",Direction.SOUTH, Direction.SOUTH),
        Arguments.arguments("L90",Direction.WEST, Direction.SOUTH),
        Arguments.arguments("L180",Direction.WEST, Direction.EAST),
        Arguments.arguments("L270",Direction.WEST, Direction.NORTH),
        Arguments.arguments("L360",Direction.WEST, Direction.WEST)
    );
  }

}
