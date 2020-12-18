package fr.syalioune.puzzle.seating;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class SeatUpdaterShould {

  @ParameterizedTest
  @MethodSource("someOccupiedAdjacentSeatsSource")
  public void shouldLetAnEmptySeatWithAdjacentOccupiedSeatsRemainEmpty(int numberOfOccupiedAdjacentSeats) {
    // Arrange
    SeatUpdater seatUpdater = new SeatUpdater();

    // Act
    SeatState seatState = seatUpdater.nextState(SeatState.EMPTY, numberOfOccupiedAdjacentSeats);

    // Assert
    Assertions.assertEquals(SeatState.EMPTY, seatState);
  }

  @Test
  public void shouldTurnAnEmptySeatWithNoAdjacentOccupiedSeatsToOccupied() {
    // Arrange
    SeatUpdater seatUpdater = new SeatUpdater();

    // Act
    SeatState seatState = seatUpdater.nextState(SeatState.EMPTY, 0);

    // Assert
    Assertions.assertEquals(SeatState.OCCUPIED, seatState);
  }

  @ParameterizedTest
  @MethodSource("fourOrMoreOccupiedAdjacentSeatsSource")
  public void shouldTurnAnOccupiedSeatWithFourOrMoreAdjacentOccupiedSeatsToEmpty(int numberOfOccupiedAdjacentSeats) {
    // Arrange
    SeatUpdater seatUpdater = new SeatUpdater();

    // Act
    SeatState seatState = seatUpdater.nextState(SeatState.OCCUPIED, numberOfOccupiedAdjacentSeats);

    // Assert
    Assertions.assertEquals(SeatState.EMPTY, seatState);
  }

  @ParameterizedTest
  @MethodSource("upToThreeOccupiedAdjacentSeatsSource")
  public void shouldNotChangeAnOccupiedSeatWithUpToThreeAdjacentOccupiedSeats(int numberOfOccupiedAdjacentSeats) {
    // Arrange
    SeatUpdater seatUpdater = new SeatUpdater();

    // Act
    SeatState seatState = seatUpdater.nextState(SeatState.OCCUPIED, numberOfOccupiedAdjacentSeats);

    // Assert
    Assertions.assertEquals(SeatState.OCCUPIED, seatState);
  }

  @ParameterizedTest
  @MethodSource("allPossibleOccupiedAdjacentSeatsSource")
  public void shouldNotChangeFloorSeats(int numberOfOccupiedAdjacentSeats) {
    // Arrange
    SeatUpdater seatUpdater = new SeatUpdater();

    // Act
    SeatState seatState = seatUpdater.nextState(SeatState.FLOOR, numberOfOccupiedAdjacentSeats);

    // Assert
    Assertions.assertEquals(SeatState.FLOOR, seatState);
  }

  static Stream<Integer> someOccupiedAdjacentSeatsSource() {
    return IntStream.range(1,9).boxed();
  }

  static Stream<Integer> fourOrMoreOccupiedAdjacentSeatsSource() {
    return IntStream.range(4,9).boxed();
  }

  static Stream<Integer> upToThreeOccupiedAdjacentSeatsSource() {
    return IntStream.range(0,4).boxed();
  }

  static Stream<Integer> allPossibleOccupiedAdjacentSeatsSource() {
    return IntStream.range(1,9).boxed();
  }

}
