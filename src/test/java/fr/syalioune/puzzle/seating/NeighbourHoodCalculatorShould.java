package fr.syalioune.puzzle.seating;

import static fr.syalioune.puzzle.seating.SeatState.EMPTY;
import static fr.syalioune.puzzle.seating.SeatState.FLOOR;
import static fr.syalioune.puzzle.seating.SeatState.OCCUPIED;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class NeighbourHoodCalculatorShould {

  private static SeatState[][] seats = {
      {EMPTY, OCCUPIED, FLOOR, EMPTY},
      {EMPTY, OCCUPIED, FLOOR, OCCUPIED},
      {EMPTY, OCCUPIED, FLOOR, FLOOR},
      {EMPTY, OCCUPIED, FLOOR, OCCUPIED}
  };

  private static Ferry FERRY = new Ferry(seats);

  @ParameterizedTest
  @MethodSource("seatsSource")
  public void shouldCorrectlyCalculateNumberOfAdjacentOccupiedSeatForBorderSeats(int x, int y, int expectedNumberOfOccupiedSeats) {
    // Arrange
    NeighbourHoodCalculator neighbourHoodCalculator = new NeighbourHoodCalculator(FERRY);

    // Act
    int numberOfOccupiedSeats = neighbourHoodCalculator.nbOfAdjacentOccupiedSeats(x,y);

    // Assert
    Assertions.assertEquals(expectedNumberOfOccupiedSeats, numberOfOccupiedSeats);
  }

  static Stream<Arguments> seatsSource() {
    return Stream.of(
        Arguments.arguments(0,0,2),
        Arguments.arguments(0,1,1),
        Arguments.arguments(0,2,3),
        Arguments.arguments(0,3,3),
        Arguments.arguments(1,0,3),
        Arguments.arguments(1,1,4),
        Arguments.arguments(1,2,4),
        Arguments.arguments(1,3,3),
        Arguments.arguments(2,0,3),
        Arguments.arguments(2,1,2),
        Arguments.arguments(2,2,5),
        Arguments.arguments(2,3,4),
        Arguments.arguments(3,0,2),
        Arguments.arguments(3,1,3),
        Arguments.arguments(3,2,3),
        Arguments.arguments(3,3,3)
    );
  }

}
