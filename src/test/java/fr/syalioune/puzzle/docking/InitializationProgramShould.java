package fr.syalioune.puzzle.docking;

import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class InitializationProgramShould {

  @ParameterizedTest
  @MethodSource("invalidMaskSource")
  public void rejectInvalidMask(String mask) {
    // Arrange
    InitializationProgram program = new InitializationProgram(mask);

    // Act
    Assertions.assertThrows(IllegalArgumentException.class, () -> program.applyValueMask(0L));

    // Assert
    // Nothing to be done
  }

  @ParameterizedTest
  @MethodSource("validValueMaskSource")
  public void correctlyApplyTheMaskToAGivenNumber(String mask, Long number, Long transformedNumber) {
    // Arrange
    InitializationProgram program = new InitializationProgram(mask);

    // Act
    Long maskedNumber = program.applyValueMask(number);

    // Assert
    Assertions.assertEquals(transformedNumber, maskedNumber);
    Assertions.assertEquals(0, program.getMemoryTotal());
  }

  @Test
  public void haveAllMemoryAddressesInitializedToZero() {
    // Arrange
    InitializationProgram program = new InitializationProgram();

    // Assert
    LongStream.range(0, 1000).boxed().forEach(address -> {
      Assertions.assertEquals(0, program.getMemory(address));
    });
    Assertions.assertEquals(0, program.getMemoryTotal());
  }

  @ParameterizedTest
  @MethodSource("validValueMaskSource")
  public void applyMaskWhenUpdatingMemoryV1(String mask, Long number, Long transformedNumber) {
    // Arrange
    InitializationProgram program = new InitializationProgram(mask);

    // Act
    program.setMemoryV1(10L, number);

    // Assert
    Assertions.assertEquals(transformedNumber, program.getMemory(10L));
    Assertions.assertEquals(transformedNumber, program.getMemoryTotal());
  }

  @ParameterizedTest
  @MethodSource("validAddressMaskSource")
  public void correctlyApplyTheMaskToAGivenAddress(String mask, Long address, List<Long> decodedAddresses) {
    // Arrange
    InitializationProgram program = new InitializationProgram(mask);

    // Act
    List<Long> addresses = program.applyAddressMask(address);

    // Assert
    Assertions.assertNotNull(addresses);
    Assertions.assertEquals(decodedAddresses.size(), addresses.size());
    decodedAddresses.forEach(ad -> {
      Assertions.assertTrue(addresses.contains(ad), () -> ad + " is not computed");
    });
  }

  @ParameterizedTest
  @MethodSource("validAddressMaskForMemoryUpdateSource")
  public void applyMaskWhenUpdatingMemoryV2(String mask, Long address, List<Long> addresses, Long value) {
    // Arrange
    InitializationProgram program = new InitializationProgram(mask);

    // Act
    program.setMemoryV2(address, value);

    // Assert
    addresses.forEach(ad -> {
      Assertions.assertEquals(value, program.getMemory(ad));
    });
    Assertions.assertEquals(addresses.size()*value, program.getMemoryTotal());
  }

  static Stream<String> invalidMaskSource() {
    return Stream.of(
        null,
        "",
        "abcd",
        "1 hjkkl",
        "10X",
        "0101"
    );
  }

  static Stream<Arguments> validValueMaskSource() {
    return Stream.of(
        Arguments.arguments("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X",11L,73L),
        Arguments.arguments("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X",101L,101L),
        Arguments.arguments("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X",0L,64L)
    );
  }

  static Stream<Arguments> validAddressMaskSource() {
    return Stream.of(
        Arguments.arguments("000000000000000000000000000000X1001X",42L, Arrays.asList(26L,27L,58L,59L)),
        Arguments.arguments("00000000000000000000000000000000X0XX",26L, Arrays.asList(16L,17L,18L,19L,24L,25L,26L,27L))
    );
  }

  static Stream<Arguments> validAddressMaskForMemoryUpdateSource() {
    return Stream.of(
        Arguments.arguments("00000000000000000000000000000000X0XX",26L, Arrays.asList(16L,17L,18L,19L,24L,25L,26L,27L),10L)
    );
  }

}
