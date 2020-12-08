package fr.syalioune.puzzle.handyhaversacks;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class MatchingBagFinderShould {

  @ParameterizedTest
  @MethodSource("noMatchingBagSource")
  public void returnAnEmptyBagListInCaseOfZeroMatch(Map<String, Set<String>> bagAffinity, String bagToMatch) {
    // Arrange
    MatchingBagFinder matchingBagFinder = new MatchingBagFinder(bagAffinity);

    // Act
    Set<String> result = matchingBagFinder.find(bagToMatch);

    // Assert
    Assertions.assertNotNull(result);
    Assertions.assertEquals(0, result.size());
  }

  @ParameterizedTest
  @MethodSource("oneLevelMatchingBagSource")
  public void returnOnlyTheAffinityListInCaseOfOnlyOneOuterMostLevel(Map<String, Set<String>> bagAffinity, String bagToMatch) {
    // Arrange
    MatchingBagFinder matchingBagFinder = new MatchingBagFinder(bagAffinity);

    // Act
    Set<String> result = matchingBagFinder.find(bagToMatch);

    // Assert
    Assertions.assertIterableEquals(bagAffinity.get(bagToMatch), result);
  }

  @ParameterizedTest
  @MethodSource("multipleLevelMatchingBagSource")
  public void returnMatchingBagsInCaseOfMoreThanOneOuterMostLevel(Map<String, Set<String>> bagAffinity, String bagToMatch, Set<String> matchingBags) {
    // Arrange
    MatchingBagFinder matchingBagFinder = new MatchingBagFinder(bagAffinity);

    // Act
    Set<String> result = matchingBagFinder.find(bagToMatch);

    // Assert
    Assertions.assertIterableEquals(matchingBags, result);
  }

  @ParameterizedTest
  @MethodSource("loopingMatchingBagSource")
  public void correctlyHandleMatchingLoops(Map<String, Set<String>> bagAffinity, String bagToMatch, Set<String> matchingBags) {
    // Arrange
    MatchingBagFinder matchingBagFinder = new MatchingBagFinder(bagAffinity);

    // Act
    Set<String> result = matchingBagFinder.find(bagToMatch);

    // Assert
    Assertions.assertIterableEquals(matchingBags, result);
  }

  static Stream<Arguments> noMatchingBagSource() {
    return Stream.of(
        Arguments.arguments(Map.of("blue", Arrays.asList("green", "red"), "yellow", Arrays.asList("purple")), "purple"),
        Arguments.arguments(Map.of("blue", Arrays.asList("green", "red"), "yellow", Arrays.asList("purple")), "red")
    );
  }

  static Stream<Arguments> oneLevelMatchingBagSource() {
    return Stream.of(
        Arguments.arguments(Map.of("blue", Arrays.asList("green", "red"), "yellow", Arrays.asList("purple")), "blue"),
        Arguments.arguments(Map.of("blue", Arrays.asList("green", "red"), "yellow", Arrays.asList("purple")), "yellow")
    );
  }

  static Stream<Arguments> multipleLevelMatchingBagSource() {
    return Stream.of(
        Arguments.arguments(
            Map.of("blue", Arrays.asList("green", "red"), "green", Arrays.asList("purple")),
            "blue",
            new TreeSet<>(Arrays.asList("green", "red", "purple"))
        ),
        Arguments.arguments(
            Map.of("blue", Arrays.asList("green", "red"), "green", Arrays.asList("purple"), "purple", Arrays.asList("black")),
            "blue",
            new TreeSet<>(Arrays.asList("green", "red", "purple", "black"))
        ),
        Arguments.arguments(
            Map.of("blue", Arrays.asList("green", "red"), "green", Arrays.asList("purple"), "purple", Arrays.asList("black")),
            "green",
            new TreeSet<>(Arrays.asList("purple", "black"))
        )
    );
  }

  static Stream<Arguments> loopingMatchingBagSource() {
    return Stream.of(
        Arguments.arguments(
            Map.of("blue", Arrays.asList("green", "red"), "green", Arrays.asList("purple"), "red", Arrays.asList("blue")),
            "blue",
            new TreeSet<>(Arrays.asList("green", "red", "purple", "blue"))
        )
    );
  }

}
