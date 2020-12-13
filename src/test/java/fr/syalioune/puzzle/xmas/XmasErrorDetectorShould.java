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
  public void detectOneErrorWithInvalidListAndOneInputAfterPreamble(List<Double> input, int preambleSize, double badNumber) {
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
  public void detectOneErrorWithInvalidListAndMoreThanOneInputAfterPreamble(List<Double> input, int preambleSize, double badNumber) {
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
        Arguments.arguments(Arrays.asList(1.0,2.0,3.0), 5)
    );
  }

  static Stream<Arguments> validListAndOneInputAfterPreambleSource() {
    return Stream.of(
        Arguments.arguments(Arrays.asList(1.0,2.0,3.0,4.0,5.0,6.0), 5)
    );
  }

  static Stream<Arguments> invalidListAndOneInputAfterPreambleSource() {
    return Stream.of(
        Arguments.arguments(Arrays.asList(1.0,2.0,3.0,4.0,5.0,20.0), 5, 20.0)
    );
  }

  static Stream<Arguments> validListAndMoreThanOneInputAfterPreambleSource() {
    return Stream.of(
        Arguments.arguments(Arrays.asList(1.0,2.0,3.0,4.0,5.0,6.0,11.0,7.0), 5)
    );
  }

  static Stream<Arguments> invalidListAndMoreThanOneOneInputAfterPreambleSource() {
    return Stream.of(
        Arguments.arguments(Arrays.asList(35.0,20.0,15.0,25.0,47.0,40.0,62.0,55.0,65.0,95.0,102.0,117.0,150.0,182.0,127.0,219.0,299.0), 5, 127.0)
    );
  }

}
