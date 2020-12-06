package fr.syalioune.puzzle.binaryboarding;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class RowFinderShould {

  @ParameterizedTest
  @MethodSource("invalidBinaryRowSearchInput")
  public void rejectTheBinarySearchRowInput(String rowDescription) {
    // Arrange
    RowFinder rowFinder = new RowFinder();

    // Act
    Assertions.assertThrows(IllegalArgumentException.class, () -> rowFinder.find(rowDescription));

    // Assert
    // Nothing to be done
  }

  @ParameterizedTest
  @MethodSource("inconclusiveBinaryRowSearchInput")
  public void narrowDownTheInitialIntervalAccordingToTheDescription(String rowDescription, int nextSearchIntervalLowerBound, int nextSearchIntervalUpperBound) {
    // Arrange
    RowFinder rowFinder = new RowFinder(128);

    // Act
    rowFinder.find(rowDescription);

    // Assert
    Assertions.assertEquals(nextSearchIntervalLowerBound, rowFinder.getSearchIntervalLowerBound());
    Assertions.assertEquals(nextSearchIntervalUpperBound, rowFinder.getSearchIntervalUpperBound());
    Assertions.assertThrows(IllegalStateException.class, () -> rowFinder.getItemNumber());
  }

  @ParameterizedTest
  @MethodSource("conclusiveBinaryRowSearchInput")
  public void findTheFinalRow(String rowDescription, int rowNumber) {
    // Arrange
    RowFinder rowFinder = new RowFinder(128);

    // Act
    rowFinder.find(rowDescription);

    // Assert
    Assertions.assertEquals(rowNumber, rowFinder.getSearchIntervalLowerBound());
    Assertions.assertEquals(rowNumber, rowFinder.getSearchIntervalUpperBound());
    Assertions.assertEquals(rowNumber, rowFinder.getItemNumber());
  }

  static Stream<String> invalidBinaryRowSearchInput() {
    return Stream.of(
        null,
        "",
        "ABD",
        "1 hjkkl",
        "324",
        "FFFFFBFBF"
    );
  }

  static Stream<Arguments> inconclusiveBinaryRowSearchInput() {
    return Stream.of(
        Arguments.arguments("F", 0, 63),
        Arguments.arguments("B", 64, 127),
        Arguments.arguments("FB", 32, 63),
        Arguments.arguments("FF", 0, 31),
        Arguments.arguments("FBF", 32, 47),
        Arguments.arguments("FBFB", 40, 47),
        Arguments.arguments("FBFBB", 44, 47),
        Arguments.arguments("FBFBBF", 44, 45)
    );
  }

  static Stream<Arguments> conclusiveBinaryRowSearchInput() {
    return Stream.of(
        Arguments.arguments("FBFBBFF", 44)
    );
  }

}
