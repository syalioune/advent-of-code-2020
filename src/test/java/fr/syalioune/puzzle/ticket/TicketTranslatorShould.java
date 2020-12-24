package fr.syalioune.puzzle.ticket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

  @ParameterizedTest
  @MethodSource("fieldCandidateSource")
  public void findPossibleCandidatesForFieldsFromTickets(Map<String,String> validValueRange, List<List<Integer>> tickets, String field, List<Integer> fieldCandidates) {
    // Arrange
    TicketTranslator ticketTranslator = new TicketTranslator(validValueRange);

    // Act
    Map<String, Set<Integer>> candidates  = ticketTranslator.fieldCandidates(tickets);

    // Assert
    Assertions.assertEquals(fieldCandidates.size(), candidates.get(field).size());
    fieldCandidates.forEach(candidate -> {
      Assertions.assertTrue(candidates.get(field).contains(candidate));
    });
  }

  @ParameterizedTest
  @MethodSource("definitiveFieldCandidateSource")
  public void findDefinitiveCandidateForFields(Map<String,String> validValueRange, Map<String,Set<Integer>> possibleCandidates, Map<String,Integer> definitiveCandidates) {
    // Arrange
    TicketTranslator ticketTranslator = new TicketTranslator(validValueRange);

    // Act
    Map<String, Integer> candidates  = ticketTranslator.definitiveCandidates(possibleCandidates);

    // Assert
    Assertions.assertEquals(definitiveCandidates.size(), candidates.size());
    definitiveCandidates.forEach((key, value) -> {
      Assertions.assertEquals(value, candidates.get(key));
    });
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

  static Stream<Arguments> fieldCandidateSource() {
    return Stream.of(
        Arguments.arguments(Map.of("class", "0-1 or 4-19", "row", "0-5 or 8-19", "seat", "0-13 or 16-19"),
            Arrays.asList(
                Arrays.asList(3,9,18)
            ),"class", Arrays.asList(2,3)),
        Arguments.arguments(Map.of("class", "0-1 or 4-19", "row", "0-5 or 8-19", "seat", "0-13 or 16-19"),
            Arrays.asList(
                Arrays.asList(3,9,18),
                Arrays.asList(15,1,5),
                Arrays.asList(5,14,9)
            ) ,"row", Arrays.asList(1,2,3)),
        Arguments.arguments(Map.of("class", "0-1 or 4-19", "row", "0-5 or 8-19", "seat", "0-13 or 16-19"),
            Arrays.asList(
                Arrays.asList(3,9,18),
                Arrays.asList(15,1,5),
                Arrays.asList(5,14,9)
            ) ,"class", Arrays.asList(2,3)),
        Arguments.arguments(Map.of("class", "0-1 or 4-19", "row", "0-5 or 8-19", "seat", "0-13 or 16-19"),
            Arrays.asList(
                Arrays.asList(3,9,18),
                Arrays.asList(15,1,5),
                Arrays.asList(5,14,9)
            ) ,"seat", Arrays.asList(3))
    );
  }

  static Stream<Arguments> definitiveFieldCandidateSource() {
    return Stream.of(
        Arguments.arguments(Map.of("class", "0-1 or 4-19", "row", "0-5 or 8-19", "seat", "0-13 or 16-19"),
            Map.of(
                "seat", Set.of(3),
                "class", Set.of(2,3),
                "row", Set.of(1,2,3)
            ),
            Map.of(
                "seat", 3,
                "class", 2,
                "row", 1
            )
        )
    );
  }

}
