package fr.syalioune.puzzle.binaryboarding;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ColumnFinderShould {

  @ParameterizedTest
  @MethodSource("invalidBinaryColumnSearchInput")
  public void rejectTheBinarySearchColumnInput(String columnDescription) {
    // Arrange
    ColumnFinder columnFinder = new ColumnFinder();

    // Act
    Assertions.assertThrows(IllegalArgumentException.class, () -> columnFinder.find(columnDescription));

    // Assert
    // Nothing to be done
  }

  @ParameterizedTest
  @MethodSource("inconclusiveBinaryColumnSearchInput")
  public void narrowDownTheInitialIntervalAccordingToTheDescription(String columnDescription, int nextSearchIntervalLowerBound, int nextSearchIntervalUpperBound) {
    // Arrange
    ColumnFinder columnFinder = new ColumnFinder(8);

    // Act
    columnFinder.find(columnDescription);

    // Assert
    Assertions.assertEquals(nextSearchIntervalLowerBound, columnFinder.getSearchIntervalLowerBound());
    Assertions.assertEquals(nextSearchIntervalUpperBound, columnFinder.getSearchIntervalUpperBound());
    Assertions.assertThrows(IllegalStateException.class, () -> columnFinder.getItemNumber());
  }

  @ParameterizedTest
  @MethodSource("conclusiveBinaryColumnSearchInput")
  public void findTheFinalColumn(String columnDescription, int columnNumber) {
    // Arrange
    ColumnFinder columnFinder = new ColumnFinder(8);

    // Act
    columnFinder.find(columnDescription);

    // Assert
    Assertions.assertEquals(columnNumber, columnFinder.getSearchIntervalLowerBound());
    Assertions.assertEquals(columnNumber, columnFinder.getSearchIntervalUpperBound());
    Assertions.assertEquals(columnNumber, columnFinder.getItemNumber());
  }

  static Stream<String> invalidBinaryColumnSearchInput() {
    return Stream.of(
        null,
        "",
        "ABD",
        "1 hjkkl",
        "324",
        "LRLR"
    );
  }

  static Stream<Arguments> inconclusiveBinaryColumnSearchInput() {
    return Stream.of(
        Arguments.arguments("R", 4, 7),
        Arguments.arguments("RL", 4, 5)
    );
  }

  static Stream<Arguments> conclusiveBinaryColumnSearchInput() {
    return Stream.of(
        Arguments.arguments("RLR", 5)
    );
  }

}
