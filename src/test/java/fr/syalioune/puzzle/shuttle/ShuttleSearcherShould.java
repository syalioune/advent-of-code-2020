package fr.syalioune.puzzle.shuttle;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ShuttleSearcherShould {

  @ParameterizedTest
  @MethodSource("invalidBusIdSource")
  public void rejectInvalidBusId(List<Integer> busIds) {
    // Arrange
    ShuttleSearcher shuttleSearcher = new ShuttleSearcher();
    Random random = new Random();
    int earliestTimestamp = random.nextInt();

    // Act
    Assertions.assertThrows(IllegalArgumentException.class, () -> shuttleSearcher.findDeparture(earliestTimestamp, busIds));

    // Assert
    // Nothing to be done
  }

  @ParameterizedTest
  @MethodSource("validBusIdSource")
  public void findTheCorrectBusIdAndEarliestDepartureTime(int earliestTimestamp, List<Integer> busIds, int busId, int departureTimestamp) {
    // Arrange
    ShuttleSearcher shuttleSearcher = new ShuttleSearcher();

    // Act
    BusDeparture departure = shuttleSearcher.findDeparture(earliestTimestamp, busIds);

    // Assert
    Assertions.assertEquals(busId, departure.getBusId());
    Assertions.assertEquals(departureTimestamp, departure.getTimestamp());
  }

  static Stream<List<Integer>> invalidBusIdSource() {
    return Stream.of(
        Collections.emptyList(),
        Arrays.asList(1,-2)
    );
  }

  static Stream<Arguments> validBusIdSource() {
    return Stream.of(
        Arguments.arguments(939, Arrays.asList(7,13,59,31,19), 59, 944)
    );
  }

}
