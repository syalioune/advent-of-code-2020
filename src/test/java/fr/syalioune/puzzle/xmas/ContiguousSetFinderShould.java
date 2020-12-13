package fr.syalioune.puzzle.xmas;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ContiguousSetFinderShould {

  @ParameterizedTest
  @MethodSource("invalidListSource")
  public void rejectInvalidList(List<Double> input, Double searchedNumber) {
    // Arrange
    ContiguousSetFinder finder = new ContiguousSetFinder();

    // Act
    Assertions.assertThrows(IllegalArgumentException.class, () -> finder.find(input, searchedNumber));

    // Assert
    // Nothing to be done
  }

  @ParameterizedTest
  @MethodSource("validListWithNoResultSource")
  public void returnAnEmptySetWhenThereAreNoResults(List<Double> input, Double searchedNumber) {
    // Arrange
    ContiguousSetFinder finder = new ContiguousSetFinder();

    // Act
    TreeSet<Double> contiguousSet = finder.find(input, searchedNumber);

    // Assert
    Assertions.assertTrue(contiguousSet.isEmpty());
  }

  @ParameterizedTest
  @MethodSource("validListWithResultSource")
  public void returnASortedSetWhenThereIsAResult(List<Double> input, Double searchedNumber, int sequenceLength, Double firstNumber, Double lastNumber) {
    // Arrange
    ContiguousSetFinder finder = new ContiguousSetFinder();

    // Act
    TreeSet<Double> contiguousSet = finder.find(input, searchedNumber);

    // Assert
    Assertions.assertFalse(contiguousSet.isEmpty());
    Assertions.assertEquals(sequenceLength, contiguousSet.size());
    Assertions.assertEquals(firstNumber, contiguousSet.first());
    Assertions.assertEquals(lastNumber, contiguousSet.last());
  }

  static Stream<Arguments> invalidListSource() {
    return Stream.of(
        Arguments.arguments(null,0.0),
        Arguments.arguments(Collections.emptyList(), 0.0),
        Arguments.arguments(Arrays.asList(1), 5.0)
    );
  }

  static Stream<Arguments> validListWithNoResultSource() {
    return Stream.of(
        Arguments.arguments(Arrays.asList(1.0,2.0,3.0,10.0), 14.0)
    );
  }

  static Stream<Arguments> validListWithResultSource() {
    return Stream.of(
        Arguments.arguments(Arrays.asList(35.0,20.0,15.0,25.0,47.0,40.0,62.0,55.0,65.0,95.0,102.0,117.0,150.0,182.0,127.0,219.0,299.0), 127.0, 4, 15.0, 47.0)
    );
  }


}
