package fr.syalioune.puzzle.handyhaversacks;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CapacityCalculatorShould {

  @ParameterizedTest
  @MethodSource("bagWithNoContentSource")
  public void returnOneWhenTheBagCannotHaveAnyContent(Map<String, Map<String,Integer>> bagCapacities, String bag) {
    // Arrange
    CapacityCalculator capacityCalculator = new CapacityCalculator(bagCapacities);

    // Act
    Integer bagCapacity = capacityCalculator.contain(bag);

    // Assert
    Assertions.assertEquals(0, bagCapacity);
  }

  @ParameterizedTest
  @MethodSource("oneLevelBagSource")
  public void returnImmediateContentCapacityWhenThereIsNoRecursion(Map<String, Map<String,Integer>> bagCapacities, String bag, Integer expectedBagCapacity) {
    // Arrange
    CapacityCalculator capacityCalculator = new CapacityCalculator(bagCapacities);

    // Act
    Integer bagCapacity = capacityCalculator.contain(bag);

    // Assert
    Assertions.assertEquals(expectedBagCapacity, bagCapacity);
  }

  @ParameterizedTest
  @MethodSource("multipleLevelBagSource")
  public void returnInnermostContentCapacityWhenThereIsRecursion(Map<String, Map<String,Integer>> bagCapacities, String bag, Integer expectedBagCapacity) {
    // Arrange
    CapacityCalculator capacityCalculator = new CapacityCalculator(bagCapacities);

    // Act
    Integer bagCapacity = capacityCalculator.contain(bag);

    // Assert
    Assertions.assertEquals(expectedBagCapacity, bagCapacity);
  }

  static Stream<Arguments> bagWithNoContentSource() {
    return Stream.of(
        Arguments.arguments(Collections.emptyMap(), "orange"),
        Arguments.arguments(Map.of("blue", Map.of("green", 2, "red", 1), "green", Map.of("yellow", 4)), "orange")
    );
  }

  static Stream<Arguments> oneLevelBagSource() {
    return Stream.of(
        Arguments.arguments(Map.of("blue", Map.of("green", 2, "red", 1), "orange", Map.of("yellow", 4)), "blue", 3),
        Arguments.arguments(Map.of("blue", Map.of("green", 2, "red", 1), "orange", Map.of("yellow", 4)), "orange", 4)
    );
  }

  static Stream<Arguments> multipleLevelBagSource() {
    return Stream.of(
        Arguments.arguments(
            Map.of("blue", Map.of("green", 2, "red", 1), "orange", Map.of("yellow", 4), "green",
                Map.of("white", 3)), "blue", 9),
        Arguments.arguments(
            Map.of(
                "shiny gold", Map.of("dark red", 2),
                "dark red", Map.of("dark orange", 2),
                "dark orange", Map.of("dark yellow", 2),
                "dark yellow", Map.of("dark green", 2),
                "dark green", Map.of("dark blue", 2),
                "dark blue", Map.of("dark violet", 2)
            ),
            "shiny gold", 126)
    );
  }

}
