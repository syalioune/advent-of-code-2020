package fr.syalioune.puzzle.binaryboarding;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class SeatFinderShould {

  @ParameterizedTest
  @MethodSource("invalidBinarySearchSeatInput")
  public void rejectTheBinarySearchSeatInput(String seatDescription) {
    // Arrange
    SeatFinder seatFinder = new SeatFinder();

    // Act
    Assertions.assertThrows(IllegalArgumentException.class, () -> seatFinder.find(seatDescription));

    // Assert
    // Nothing to be done
  }

  @ParameterizedTest
  @MethodSource("validBinarySearchSeatInput")
  public void findTheCorrectSeat(String seatDescription, int row, int column, int expectedSeatId) {
    // Arrange
    SeatFinder seatFinder = new SeatFinder();

    // Act
    int seatId = seatFinder.find(seatDescription);

    // Assert
    Assertions.assertEquals(row, seatFinder.getRow());
    Assertions.assertEquals(column, seatFinder.getColumn());
    Assertions.assertEquals(expectedSeatId, seatId);
  }

  static Stream<String> invalidBinarySearchSeatInput() {
    return Stream.of(
        null,
        "",
        "ABD",
        "1 hjkkl",
        "324",
        "FFFFFBFBF"
    );
  }

  static Stream<Arguments> validBinarySearchSeatInput() {
    return Stream.of(
        Arguments.arguments("BFFFBBFRRR", 70, 7, 567),
        Arguments.arguments("FFFBBBFRRR", 14, 7, 119),
        Arguments.arguments("BBFFBBFRLL", 102, 4, 820)
    );
  }
}
