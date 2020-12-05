package fr.syalioune.puzzle.reportrepair;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Unit test class for Report repair puzzle.
 */
public class ReportRepairShould {

  public static final int EXPECTED_NUMBER = 2020;

  @ParameterizedTest
  @MethodSource("listWithLessThanTwoElementsSource")
  public void returnEmptyTupleWhenListHasLessThanTwoElements(List<Integer> expenses) {
    // Arrange
    ReportRepair report = new ReportRepair();

    // Act
    Tuple result = report.findTuple(expenses, EXPECTED_NUMBER);

    // Assert
    Assertions.assertEquals(Tuple.EMPTY, result);
  }

  @ParameterizedTest
  @MethodSource("listWithNoMatchingElementsSource")
  public void returnEmptyTupleWhenNoElementsMatch(List<Integer> expenses) {
    // Arrange
    ReportRepair report = new ReportRepair();

    // Act
    Tuple result = report.findTuple(expenses, EXPECTED_NUMBER);

    // Assert
    Assertions.assertEquals(Tuple.EMPTY, result);
  }

  @ParameterizedTest
  @MethodSource("listWithMatchingElementSource")
  public void returnCorrectTupleWhenThereAreMatchingElements(List<Integer> expenses) {
    // Arrange
    ReportRepair report = new ReportRepair();

    // Act
    Tuple result = report.findTuple(expenses, EXPECTED_NUMBER);

    // Assert
    Assertions.assertEquals(EXPECTED_NUMBER, result.sum());
    Assertions.assertTrue(expenses.contains(result.getFirst()));
    Assertions.assertTrue(expenses.contains(result.getSecond()));
  }

  static Stream<List<Integer>> listWithLessThanTwoElementsSource() {
    return Stream.of(
        null,
        Collections.emptyList(),
        Arrays.asList(1)
    );
  }

  static Stream<List<Integer>> listWithNoMatchingElementsSource() {
    return Stream.of(
        Arrays.asList(EXPECTED_NUMBER, EXPECTED_NUMBER, EXPECTED_NUMBER, 1),
        Arrays.asList(5,2,3,4,5),
        Arrays.asList(1,1,1,20,EXPECTED_NUMBER)
    );
  }

  static Stream<List<Integer>> listWithMatchingElementSource() {
    return Stream.of(
        Arrays.asList(1721,379,366,299,675,1456),
        Arrays.asList(EXPECTED_NUMBER-1, EXPECTED_NUMBER, 1, 0),
        Arrays.asList(null, 0, EXPECTED_NUMBER, 2, EXPECTED_NUMBER-2)
    );
  }

}
