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
  public void shouldMoveTheWaypointInTheCorrectCardinalDirection(String instruction, int north, int south, int east, int west, Direction direction) {
    // Arrange
    Ferry ferry = new Ferry();

    // Act
    ferry.move(instruction);

    // Assert
    Assertions.assertEquals(0, ferry.getNorth());
    Assertions.assertEquals(0, ferry.getSouth());
    Assertions.assertEquals(0, ferry.getEast());
    Assertions.assertEquals(0, ferry.getWest());
    Assertions.assertEquals(north, ferry.getWaypoint().getNorth());
    Assertions.assertEquals(south, ferry.getWaypoint().getSouth());
    Assertions.assertEquals(east, ferry.getWaypoint().getEast());
    Assertions.assertEquals(west, ferry.getWaypoint().getWest());
  }

  @ParameterizedTest
  @MethodSource("validForwardDirectionSource")
  public void shouldMoveTheFerryInTheCorrectForwardDirection(String instruction, int north, int south, int east, int west, int factor) {
    // Arrange
    Ferry ferry = new Ferry();

    // Act
    ferry.move(instruction);

    // Assert
    Assertions.assertEquals(north, ferry.getWaypoint().getNorth());
    Assertions.assertEquals(south, ferry.getWaypoint().getSouth());
    Assertions.assertEquals(east, ferry.getWaypoint().getEast());
    Assertions.assertEquals(west, ferry.getWaypoint().getWest());
    Assertions.assertEquals(factor*north, ferry.getNorth());
    Assertions.assertEquals(factor*south, ferry.getSouth());
    Assertions.assertEquals(factor*east, ferry.getEast());
    Assertions.assertEquals(factor*west, ferry.getWest());
  }

  @ParameterizedTest
  @MethodSource("validRotateDirectionSource")
  public void shouldRotateTheWaypointInTheCorrectDirection(String instruction, int north, int east, int south, int west) {
    // Arrange
    Ferry ferry = new Ferry();

    // Act
    ferry.move(instruction);

    // Assert
    Assertions.assertEquals(0, ferry.getNorth());
    Assertions.assertEquals(0, ferry.getSouth());
    Assertions.assertEquals(0, ferry.getEast());
    Assertions.assertEquals(0, ferry.getWest());
    Assertions.assertEquals(north, ferry.getWaypoint().getNorth());
    Assertions.assertEquals(south, ferry.getWaypoint().getSouth());
    Assertions.assertEquals(east, ferry.getWaypoint().getEast());
    Assertions.assertEquals(west, ferry.getWaypoint().getWest());
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
        Arguments.arguments("N3",4,0,10,0,Direction.EAST),
        Arguments.arguments("S3",1,3,10,0,Direction.EAST),
        Arguments.arguments("E3",1,0,13,0,Direction.EAST),
        Arguments.arguments("W3",1,0,10,3,Direction.EAST)
    );
  }

  static Stream<Arguments> validForwardDirectionSource() {
    return Stream.of(
        Arguments.arguments("F10",1,0,10,0,10)
    );
  }

  static Stream<Arguments> validRotateDirectionSource() {
    return Stream.of(
        Arguments.arguments("R360",1,10,0,0),
        Arguments.arguments("R90",0,1,10,0),
        Arguments.arguments("R180",0,0,1,10),
        Arguments.arguments("R270",10,0,0,1),
        Arguments.arguments("L360",1,10,0,0),
        Arguments.arguments("L90",10,0,0,1),
        Arguments.arguments("L180",0,0,1,10),
        Arguments.arguments("L270",0,1,10,0)
    );
  }

}
