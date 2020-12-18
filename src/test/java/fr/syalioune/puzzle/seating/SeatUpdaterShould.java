package fr.syalioune.puzzle.seating;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
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
  @MethodSource("fiveOrMoreOccupiedAdjacentSeatsSource")
  public void shouldTurnAnOccupiedSeatWithFiveOrMoreAdjacentOccupiedSeatsToEmpty(int numberOfOccupiedAdjacentSeats) {
    // Arrange
    SeatUpdater seatUpdater = new SeatUpdater();

    // Act
    SeatState seatState = seatUpdater.nextState(SeatState.OCCUPIED, numberOfOccupiedAdjacentSeats);

    // Assert
    Assertions.assertEquals(SeatState.EMPTY, seatState);
  }

  @ParameterizedTest
  @MethodSource("upToFourOccupiedAdjacentSeatsSource")
  public void shouldNotChangeAnOccupiedSeatWithUpToFourAdjacentOccupiedSeats(int numberOfOccupiedAdjacentSeats) {
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

  static Stream<Integer> fiveOrMoreOccupiedAdjacentSeatsSource() {
    return IntStream.range(5,9).boxed();
  }

  static Stream<Integer> upToFourOccupiedAdjacentSeatsSource() {
    return IntStream.range(0,5).boxed();
  }

  static Stream<Integer> allPossibleOccupiedAdjacentSeatsSource() {
    return IntStream.range(1,9).boxed();
  }

}
