package fr.syalioune.puzzle.ticket;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TicketTranslatorShould {

  @ParameterizedTest
  @MethodSource("invalidValueRangeSource")
  public void rejectInvalidValueRanges(Map<String,String> invalidValueRange) {
    // Arrange
    Assertions.assertThrows(IllegalArgumentException.class, () -> new TicketTranslator(invalidValueRange));

    // Act
    // Nothing to be done

    // Assert
    // Nothing to be done
  }

  @ParameterizedTest
  @MethodSource("validValueSource")
  public void acceptValueInTheAcceptedRange(Map<String,String> validValueRange, Integer value) {
    // Arrange
    TicketTranslator ticketTranslator = new TicketTranslator(validValueRange);

    // Act
    Boolean validValue = ticketTranslator.isValid(value);

    // Assert
    Assertions.assertTrue(validValue);
  }

  @ParameterizedTest
  @MethodSource("invalidValueSource")
  public void rejectValueOutsideTheAcceptedRange(Map<String,String> validValueRange, Integer value) {
    // Arrange
    TicketTranslator ticketTranslator = new TicketTranslator(validValueRange);

    // Act
    Boolean validValue = ticketTranslator.isValid(value);

    // Assert
    Assertions.assertFalse(validValue);
  }

  static Stream<Arguments> validValueSource() {
    return Stream.of(
        Arguments.arguments(Map.of("class", "1-3 or 5-7", "row", "6-11 or 33-44", "seat", "13-40 or 45-50"),7),
        Arguments.arguments(Map.of("class", "1-3 or 5-7", "row", "6-11 or 33-44", "seat", "13-40 or 45-50"),1),
        Arguments.arguments(Map.of("class", "1-3 or 5-7", "row", "6-11 or 33-44", "seat", "13-40 or 45-50"),14)
    );
  }

  static Stream<Map<String,String>> invalidValueRangeSource() {
    return Stream.of(
        null,
        Collections.emptyMap(),
        Map.of("a", "hildkl"),
        Map.of("a", "1-a"),
        Map.of("a", "1-2 or"),
        Map.of("a", "1-3 or a-")
    );
  }

  static Stream<Arguments> invalidValueSource() {
    return Stream.of(
        Arguments.arguments(Map.of("class", "1-3 or 5-7", "row", "6-11 or 33-44", "seat", "13-40 or 45-50"),4),
        Arguments.arguments(Map.of("class", "1-3 or 5-7", "row", "6-11 or 33-44", "seat", "13-40 or 45-50"),55),
        Arguments.arguments(Map.of("class", "1-3 or 5-7", "row", "6-11 or 33-44", "seat", "13-40 or 45-50"),12)
    );
  }

}
