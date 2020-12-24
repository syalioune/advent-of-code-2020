package fr.syalioune.puzzle.recitation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class RepeaterShould {

  @ParameterizedTest
  @MethodSource("invalidStartingNumbersSource")
  public void rejectInvalidStartingNumbers(List<Integer> startingNumbers) {
    // Arrange && Act && Assert
    Assertions.assertThrows(IllegalArgumentException.class, () -> new Repeater(startingNumbers));
  }

  @ParameterizedTest
  @MethodSource("invalidTurnIdSource")
  public void rejectInvalidTurnId(List<Integer> startingNumbers, Integer turnId) {
    // Arrange
    Repeater repeater = new Repeater(startingNumbers);

    // Act && Assert
    Assertions.assertThrows(IllegalArgumentException.class, () -> repeater.spokenNumber(turnId));
  }

  @ParameterizedTest
  @MethodSource("validStartingNumbersAndTurnIdsBelowInitialSizeSource")
  public void returnInitialListElementsWhenTheTurnIdIsBelowStartingNumbersListSize(List<Integer> startingNumbers, Integer turnId) {
    // Arrange
    Repeater repeater = new Repeater(startingNumbers);

    // Act
    Integer spokenNumber = repeater.spokenNumber(turnId);

    // Assert
    Assertions.assertEquals(startingNumbers.get(turnId-1), spokenNumber);
  }

  @ParameterizedTest
  @MethodSource("validStartingNumbersAndTurnIdsAboveInitialSizeSource")
  public void computeNewElementsWhenTheTurnIdIsAboveStartingNumbersListSize(List<Integer> startingNumbers, Integer turnId, Integer expectedSpokenNumber) {
    // Arrange
    Repeater repeater = new Repeater(startingNumbers);

    // Act
    Integer spokenNumber = repeater.spokenNumber(turnId);

    // Assert
    Assertions.assertEquals(expectedSpokenNumber, spokenNumber);
  }

  static Stream<List<Integer>> invalidStartingNumbersSource() {
    return Stream.of(
        null,
        Collections.emptyList()
    );
  }

  static Stream<Arguments> invalidTurnIdSource() {
    return Stream.of(
        Arguments.arguments(Arrays.asList(0,3,6), -1)
    );
  }

  static Stream<Arguments> validStartingNumbersAndTurnIdsBelowInitialSizeSource() {
    return Stream.of(
        Arguments.arguments(Arrays.asList(0,3,6), 2)
    );
  }

  static Stream<Arguments> validStartingNumbersAndTurnIdsAboveInitialSizeSource() {
    return Stream.of(
        Arguments.arguments(Arrays.asList(0,3,6), 4, 0),
        Arguments.arguments(Arrays.asList(0,3,6), 5, 3),
        Arguments.arguments(Arrays.asList(0,3,6), 6, 3),
        Arguments.arguments(Arrays.asList(0,3,6), 7, 1),
        Arguments.arguments(Arrays.asList(0,3,6), 8, 0),
        Arguments.arguments(Arrays.asList(0,3,6), 9, 4),
        Arguments.arguments(Arrays.asList(0,3,6), 10, 0),
        Arguments.arguments(Arrays.asList(1,3,2), 2020, 1),
        Arguments.arguments(Arrays.asList(2,1,3), 2020, 10),
        Arguments.arguments(Arrays.asList(1,2,3), 2020, 27),
        Arguments.arguments(Arrays.asList(2,3,1), 2020, 78),
        Arguments.arguments(Arrays.asList(3,2,1), 2020, 438),
        Arguments.arguments(Arrays.asList(3,1,2), 2020, 1836)
    );
  }

}
