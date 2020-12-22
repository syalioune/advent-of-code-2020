package fr.syalioune.puzzle.docking;

import java.util.stream.IntStream;
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
    Assertions.assertThrows(IllegalArgumentException.class, () -> program.applyMask(0L));

    // Assert
    // Nothing to be done
  }

  @ParameterizedTest
  @MethodSource("validMaskSource")
  public void correctlyApplyTheMaskToAGivenNumber(String mask, Long number, Long transformedNumber) {
    // Arrange
    InitializationProgram program = new InitializationProgram(mask);

    // Act
    Long maskedNumber = program.applyMask(number);

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
  @MethodSource("validMaskSource")
  public void applyMaskWhenUpdatingMemory(String mask, Long number, Long transformedNumber) {
    // Arrange
    InitializationProgram program = new InitializationProgram(mask);

    // Act
    program.setMemory(10L, number);

    // Assert
    Assertions.assertEquals(transformedNumber, program.getMemory(10L));
    Assertions.assertEquals(transformedNumber, program.getMemoryTotal());
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

  static Stream<Arguments> validMaskSource() {
    return Stream.of(
        Arguments.arguments("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X",11L,73L),
        Arguments.arguments("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X",101L,101L),
        Arguments.arguments("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X",0L,64L)
    );
  }

}
