package fr.syalioune.puzzle.xmas;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class XmasErrorDetectorShould {

  @ParameterizedTest
  @MethodSource("invalidListSource")
  public void rejectInvalidList(List<Double> input, int preambleSize) {
    // Arrange
    XmasErrorDetector xmasErrorDetector = new XmasErrorDetector(preambleSize);

    // Act
    Assertions.assertThrows(IllegalArgumentException.class, () -> xmasErrorDetector.scan(input));

    // Assert
    // Nothing to be done
  }

  @ParameterizedTest
  @MethodSource("validListAndOneInputAfterPreambleSource")
  public void detectZeroErrorWithValidListAndOneInputAfterPreamble(List<Double> input, int preambleSize) {
    // Arrange
    XmasErrorDetector xmasErrorDetector = new XmasErrorDetector(preambleSize);

    // Act
    xmasErrorDetector.scan(input);

    // Assert
    // Nothing to be done
  }

  @ParameterizedTest
  @MethodSource("invalidListAndOneInputAfterPreambleSource")
  public void detectOneErrorWithInvalidListAndOneInputAfterPreamble(List<Double> input, int preambleSize, int badNumber) {
    // Arrange
    XmasErrorDetector xmasErrorDetector = new XmasErrorDetector(preambleSize);

    // Act
    XmasEncodingException xmasException = Assertions.assertThrows(XmasEncodingException.class, () -> xmasErrorDetector
        .scan(input));

    // Assert
    Assertions.assertEquals(badNumber, xmasException.getNumber());
  }

  @ParameterizedTest
  @MethodSource("validListAndMoreThanOneInputAfterPreambleSource")
  public void detectZeroErrorWithValidListAndMoreThanOneInputAfterPreamble(List<Double> input, int preambleSize) {
    // Arrange
    XmasErrorDetector xmasErrorDetector = new XmasErrorDetector(preambleSize);

    // Act
    xmasErrorDetector.scan(input);

    // Assert
    // Nothing to be done
  }

  @ParameterizedTest
  @MethodSource("invalidListAndMoreThanOneOneInputAfterPreambleSource")
  public void detectOneErrorWithInvalidListAndMoreThanOneInputAfterPreamble(List<Double> input, int preambleSize, int badNumber) {
    // Arrange
    XmasErrorDetector xmasErrorDetector = new XmasErrorDetector(preambleSize);

    // Act
    XmasEncodingException xmasException = Assertions.assertThrows(XmasEncodingException.class, () -> xmasErrorDetector
        .scan(input));

    // Assert
    Assertions.assertEquals(badNumber, xmasException.getNumber());
  }

  static Stream<Arguments> invalidListSource() {
    return Stream.of(
        Arguments.arguments(null,0),
        Arguments.arguments(Collections.emptyList(), 0),
        Arguments.arguments(Arrays.asList(1,2,3), 5)
    );
  }

  static Stream<Arguments> validListAndOneInputAfterPreambleSource() {
    return Stream.of(
        Arguments.arguments(Arrays.asList(1,2,3,4,5,6), 5)
    );
  }

  static Stream<Arguments> invalidListAndOneInputAfterPreambleSource() {
    return Stream.of(
        Arguments.arguments(Arrays.asList(1,2,3,4,5,20), 5, 20)
    );
  }

  static Stream<Arguments> validListAndMoreThanOneInputAfterPreambleSource() {
    return Stream.of(
        Arguments.arguments(Arrays.asList(1,2,3,4,5,6,11,7), 5)
    );
  }

  static Stream<Arguments> invalidListAndMoreThanOneOneInputAfterPreambleSource() {
    return Stream.of(
        Arguments.arguments(Arrays.asList(35,20,15,25,47,40,62,55,65,95,102,117,150,182,127,219,299), 5, 127)
    );
  }

}
