package fr.syalioune.puzzle.adapter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class JoltAdapterShould {

  @ParameterizedTest
  @MethodSource("invalidListSource")
  public void rejectInvalidInput(List<Integer> inputs) {
    // Arrange
    JoltAdapter adapter = new JoltAdapter();

    // Act
    Assertions.assertThrows(IllegalArgumentException.class, () -> adapter.arrange(inputs));

    // Assert
  }

  @ParameterizedTest
  @MethodSource("validListSource")
  public void correctlyBuildAdapterDifferenceDistribution(List<Integer> inputs, Map<Integer, Integer> expectedDistribution) {
    // Arrange
    JoltAdapter adapter = new JoltAdapter();

    // Act
    Map<Integer, Integer> distribution = adapter.arrange(inputs);

    // Assert
    Assertions.assertEquals(expectedDistribution.size(), distribution.size());
    expectedDistribution.forEach((key, value) -> {
      Assertions.assertEquals(expectedDistribution.get(key), distribution.get(key));
    });
  }

  @ParameterizedTest
  @MethodSource("validListForArrangementSource")
  public void correctlyBuildAdapterArrangement(List<Integer> inputs, long numberOfArrangements) {
    // Arrange
    JoltAdapter adapter = new JoltAdapter();

    // Act
    long possibilities = adapter.countArrangements(inputs);

    // Assert
    Assertions.assertEquals(numberOfArrangements, possibilities);
  }

  static Stream<List<Integer>> invalidListSource() {
    return Stream.of(
        null,
        Collections.emptyList(),
        Arrays.asList(10,4,5,9)
    );
  }

  static Stream<Arguments> validListSource() {
    return Stream.of(
        Arguments.arguments(Arrays.asList(16,10,15,5,1,11,7,19,6,12,4), Map.of(1,7, 3,5)),
        Arguments.arguments(Arrays.asList(28,33,18,42,31,14,46,20,48,47,24,23,49,45,19,38,39,11,1,32,25,35,8,17,7,9,4,2,34,10,3), Map.of(1,22, 3,10))
    );
  }

  static Stream<Arguments> validListForArrangementSource() {
    return Stream.of(
        Arguments.arguments(Arrays.asList(1,4,5,6), 2),
        Arguments.arguments(Arrays.asList(1,4,5,6,7), 4),
        Arguments.arguments(Arrays.asList(16,10,15,5,1,11,7,19,6,12,4), 8),
        Arguments.arguments(Arrays.asList(28,33,18,42,31,14,46,20,48,47,24,23,49,45,19,38,39,11,1,32,25,35,8,17,7,9,4,2,34,10,3), 19208)
    );
  }

}
