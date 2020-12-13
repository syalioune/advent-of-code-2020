package fr.syalioune.puzzle.codeexecutor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ExecutorShould {

  @ParameterizedTest
  @MethodSource("invalidInstructionSource")
  public void rejectInvalidInstructions(String invalidInstruction) {
    // Arrange
    Executor executor = new Executor(0);

    // Act
    Assertions.assertThrows(IllegalArgumentException.class, () -> executor.process(invalidInstruction));

    // Assert
    // Nothing to be done
  }

  @Test
  public void shouldDoNothingWhenGivenNopInstruction() {
    // Arrange
    Executor executor = new Executor(0);

    // Act
    executor.process("nop +4");

    // Assert
    // Nothing to be done
  }

  @ParameterizedTest
  @MethodSource("accInstructionSource")
  public void shouldUpdateTheAccumulatorWhenGivenAccInstruction(Integer initialValue, String instruction, Integer finalValue) {
    // Arrange
    Executor executor = new Executor(initialValue);

    // Act
    executor.process(instruction);

    // Assert
    Assertions.assertEquals(finalValue, executor.getAccumulator());
  }

  @ParameterizedTest
  @MethodSource("validJmpInstructionSource")
  public void shouldUpdateTheExecutorIndexWhenGivenValidJmpInstruction(Integer instructionCapacity, List<String> instructions, Integer finalIndex) {
    // Arrange
    Executor executor = new Executor(0, instructionCapacity);

    // Act
    instructions.forEach(instruction -> {
      executor.process(instruction);
    });

    // Assert
    Assertions.assertEquals(finalIndex, executor.getCurrentInstructionIndex());
  }

  @ParameterizedTest
  @MethodSource("invalidJmpInstructionSource")
  public void shouldRejectAnyJumpInstructionOutOfBound(Integer instructionCapacity, List<String> instructions) {
    // Arrange
    Executor executor = new Executor(0, instructionCapacity);

    // Act
    Assertions.assertThrows(IllegalStateException.class, () -> {
      instructions.forEach(instruction -> {
        executor.process(instruction);
      });
    });

    // Assert
    // Nothing to be done
  }

  @ParameterizedTest
  @MethodSource("loopingCommandListSource")
  public void shouldDetectLoopingInstructionList(List<String> instructions) {
    // Arrange
    Executor executor = new Executor(0, instructions.size());

    // Act
    Assertions.assertThrows(LoopException.class, () -> {
      instructions.forEach(instruction -> {
        executor.process(instruction);
      });
    });

    // Assert
    // Nothing to be done
  }

  static Stream<String> invalidInstructionSource() {
    return Stream.of(
        null,
        "",
        "ABD",
        "1 hjkkl",
        "324",
        "FFFFFBFBF",
        "acc   +78",
        "acc 1",
        "jmp 7",
        "jmp  8"
    );
  }

  static Stream<Arguments> accInstructionSource() {
    return Stream.of(
        Arguments.arguments(0, "acc +1", 1),
        Arguments.arguments(-1, "acc -99", -100)
    );
  }

  static Stream<Arguments> validJmpInstructionSource() {
    return Stream.of(
        Arguments.arguments(10, Arrays.asList("jmp +2", "jmp +4", "jmp -3"), 4)
    );
  }

  static Stream<Arguments> invalidJmpInstructionSource() {
    return Stream.of(
        Arguments.arguments(10, Arrays.asList("jmp +2", "jmp +4", "jmp -10")),
        Arguments.arguments(10, Arrays.asList("jmp +15", "jmp +4", "jmp -10")),
        Arguments.arguments(5, Arrays.asList("jmp +2", "jmp +4"))
    );
  }

  static Stream<List<String>> loopingCommandListSource() {
    return Stream.of(
        Arrays.asList("nop +0", "acc +1", "jmp +4", "acc +3", "jmp -3", "acc -99", "acc +1", "jmp -4", "acc +6")
    );
  }

}
