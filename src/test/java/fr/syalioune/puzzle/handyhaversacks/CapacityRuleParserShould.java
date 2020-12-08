package fr.syalioune.puzzle.handyhaversacks;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CapacityRuleParserShould {

  @ParameterizedTest
  @MethodSource("invalidRuleSource")
  public void rejectInvalidRules(String invalidRule) {
    // Arrange
    CapacityRuleParser capacityRuleParser = new CapacityRuleParser();

    // Act
    Assertions.assertThrows(IllegalArgumentException.class, () -> capacityRuleParser.parse(invalidRule));

    // Assert
    // Nothing to be done
  }

  @ParameterizedTest
  @MethodSource("rulesWithOnlyTwoBagsSource")
  public void correctlyMapRulesWithOnlyTwoBags(String rule, String firstBag, String secondBag, Integer capacity) {
    // Arrange
    CapacityRuleParser capacityRuleParser = new CapacityRuleParser();

    // Act
    Map<String, Map<String, Integer>> capacities = capacityRuleParser.parse(rule);

    // Assert
    Assertions.assertEquals(1, capacities.size());
    Assertions.assertEquals(1,capacities.get(firstBag).size());
    Assertions.assertEquals(capacity, capacities.get(firstBag).get(secondBag));
  }

  @ParameterizedTest
  @MethodSource("rulesWithMoreThanTwoBagsSource")
  public void correctlyMapRulesWithMoreThanTwoBags(String rule, String firstBag, List<String> bags, List<Integer> capacities) {
    // Arrange
    CapacityRuleParser capacityRuleParser = new CapacityRuleParser();

    // Act
    Map<String, Map<String, Integer>> bagCapacities = capacityRuleParser.parse(rule);

    // Assert
    Assertions.assertEquals(1, bagCapacities.size());
    for (int i = 0; i < bags.size(); i++) {
      Assertions.assertEquals(capacities.get(i), bagCapacities.get(firstBag).get(bags.get(i)));
    }
  }

  @ParameterizedTest
  @MethodSource("multipleRulesSource")
  public void correctlyMapMultipleRules(List<String> rules, Map<String,Map<String, Integer>> expectedCapacities) {
    // Arrange
    CapacityRuleParser capacityRuleParser = new CapacityRuleParser();

    // Act
    Map<String, Map<String, Integer>> capacities = capacityRuleParser.parse(rules);

    // Assert
    Assertions.assertEquals(expectedCapacities.size(), capacities.size());
    Assertions.assertEquals(expectedCapacities.keySet().size(), capacities.keySet().size());
    Assertions.assertAll(expectedCapacities.keySet().stream().map(expectedKey -> {
      return () -> {
        Assertions.assertTrue(capacities.containsKey(expectedKey));
        expectedCapacities.get(expectedKey).forEach((key, value) -> {
          Assertions.assertEquals(value, capacities.get(expectedKey).get(key));
        });
      };
    }));
  }

  @ParameterizedTest
  @MethodSource("voidRuleSource")
  public void correctlyIgnoreVoidRule(String rule) {
    // Arrange
    CapacityRuleParser capacityRuleParser = new CapacityRuleParser();

    // Act
    Map<String, Map<String, Integer>> bagCapacities = capacityRuleParser.parse(rule);

    // Assert
    Assertions.assertEquals(0, bagCapacities.size());
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
        Arguments.arguments("bright white bags contain 1 shiny gold bag.", "bright white", "shiny gold", 1),
        Arguments.arguments("blue bags contain 2 red bags.", "blue", "red", 2)
    );
  }

  static Stream<Arguments> rulesWithMoreThanTwoBagsSource() {
    return Stream.of(
        Arguments.arguments("light red bags contain 1 bright white bag, 2 muted yellow bags.", "light red",
            Arrays.asList("bright white", "muted yellow"), Arrays.asList(1, 2)),
        Arguments.arguments("dark orange bags contain 3 bright white bags, 4 muted yellow bags.", "dark orange",
            Arrays.asList("bright white", "muted yellow"), Arrays.asList(3, 4))
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
              "light red", Map.of("bright white", 1, "muted yellow", 2),
              "dark orange", Map.of("bright white",3,  "muted yellow", 4),
              "bright white", Map.of("shiny gold", 1)
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
