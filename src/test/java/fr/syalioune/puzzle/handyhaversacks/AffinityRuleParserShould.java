package fr.syalioune.puzzle.handyhaversacks;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AffinityRuleParserShould {

  @ParameterizedTest
  @MethodSource("invalidRuleSource")
  public void rejectInvalidRules(String invalidRule) {
    // Arrange
    AffinityRuleParser affinityRuleParser = new AffinityRuleParser();

    // Act
    Assertions.assertThrows(IllegalArgumentException.class, () -> affinityRuleParser.parse(invalidRule));

    // Assert
    // Nothing to be done
  }

  @ParameterizedTest
  @MethodSource("rulesWithOnlyTwoBagsSource")
  public void correctlyMapRulesWithOnlyTwoBags(String rule, String firstBag, String secondBag) {
    // Arrange
    AffinityRuleParser affinityRuleParser = new AffinityRuleParser();

    // Act
    Map<String, Set<String>> affinities = affinityRuleParser.parse(rule);

    // Assert
    Assertions.assertEquals(1, affinities.size());
    Assertions.assertEquals(1,affinities.get(secondBag).size());
    Assertions.assertTrue(affinities.get(secondBag).contains(firstBag));
  }

  @ParameterizedTest
  @MethodSource("rulesWithMoreThanTwoBagsSource")
  public void correctlyMapRulesWithMoreThanTwoBags(String rule, String firstBag, List<String> bags) {
    // Arrange
    AffinityRuleParser affinityRuleParser = new AffinityRuleParser();

    // Act
    Map<String, Set<String>> affinities = affinityRuleParser.parse(rule);

    // Assert
    Assertions.assertEquals(bags.size(), affinities.size());
    Assertions.assertAll(bags.stream().map(bag -> {
      return () -> {
        Assertions.assertEquals(1, affinities.get(bag).size());
        Assertions.assertTrue(affinities.get(bag).contains(firstBag));
      };
    }));
  }

  @ParameterizedTest
  @MethodSource("multipleRulesSource")
  public void correctlyMapMultipleRules(List<String> rules, Map<String,Set<String>> expectedAffinities) {
    // Arrange
    AffinityRuleParser affinityRuleParser = new AffinityRuleParser();

    // Act
    Map<String, Set<String>> affinities = affinityRuleParser.parse(rules);

    // Assert
    Assertions.assertEquals(expectedAffinities.size(), affinities.size());
    Assertions.assertEquals(expectedAffinities.keySet().size(), affinities.keySet().size());
    Assertions.assertAll(expectedAffinities.keySet().stream().map(expectedKey -> {
      return () -> {
        Assertions.assertTrue(affinities.containsKey(expectedKey));
        Assertions.assertIterableEquals(expectedAffinities.get(expectedKey), affinities.get(expectedKey));
      };
    }));
  }

  @ParameterizedTest
  @MethodSource("voidRuleSource")
  public void correctlyIgnoreVoidRule(String rule) {
    // Arrange
    AffinityRuleParser affinityRuleParser = new AffinityRuleParser();

    // Act
    Map<String, Set<String>> affinities = affinityRuleParser.parse(rule);

    // Assert
    Assertions.assertEquals(0, affinities.size());
  }

  static Stream<String> invalidRuleSource() {
    return Stream.of(
        null,
        "",
        " ",
        "abcgx"
    );
  }

  static Stream<Arguments> rulesWithOnlyTwoBagsSource() {
    return Stream.of(
        Arguments.arguments("bright white bags contain 1 shiny gold bag.", "bright white", "shiny gold"),
        Arguments.arguments("blue bags contain 2 red bags.", "blue", "red")
    );
  }

  static Stream<Arguments> rulesWithMoreThanTwoBagsSource() {
    return Stream.of(
        Arguments.arguments("light red bags contain 1 bright white bag, 2 muted yellow bags.", "light red",
            Arrays.asList("bright white", "muted yellow")),
        Arguments.arguments("dark orange bags contain 3 bright white bags, 4 muted yellow bags.", "dark orange",
            Arrays.asList("bright white", "muted yellow")),
        Arguments.arguments("vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.", "vibrant plum",
            Arrays.asList("faded blue", "dotted black"))
    );
  }

  static Stream<Arguments> multipleRulesSource() {
    return Stream.of(
        Arguments.arguments(
            Arrays.asList(
              "light red bags contain 1 bright white bag, 2 muted yellow bags.",
              "dark orange bags contain 3 bright white bags, 4 muted yellow bags.",
              "bright white bags contain 1 shiny gold bag."
            ),
            Map.of(
              "bright white", Arrays.asList("dark orange", "light red"),
              "muted yellow", Arrays.asList("dark orange", "light red"),
              "shiny gold", Arrays.asList("bright white")
            )
        )
    );
  }

  static Stream<String> voidRuleSource() {
    return Stream.of(
        "faded blue bags contain no other bags."
    );
  }

}
