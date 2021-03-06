package fr.syalioune.puzzle.customs;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class GroupAnswerCollectorShould {

  @ParameterizedTest
  @MethodSource("invalidAnswersSource")
  public void rejectAnswersWithIncorrectFormat(String incorrectAnswer) {
    // Arrange
    GroupAnswerCollector groupAnswerCollector = new GroupAnswerCollector();

    // Act
    Assertions.assertThrows(IllegalArgumentException.class, () -> groupAnswerCollector.collect(incorrectAnswer));

    // Assert
    // Nothing to be done
  }

  @ParameterizedTest
  @MethodSource("singleAnswerWithYesFromAnyOneSource")
  public void correctlyListDistinctQuestionsWithAtLeastOneYesFromSingleAnswer(String answer, int nbOfYesAnswers) {
    // Arrange
    GroupAnswerCollector groupAnswerCollector = new GroupAnswerCollector();

    // Act
    groupAnswerCollector.collect(answer);

    // Assert
    Assertions.assertEquals(nbOfYesAnswers, groupAnswerCollector.getDistinctQuestionsWithYesAnswers().size());
  }

  @ParameterizedTest
  @MethodSource("multipleAnswerWithYesFromAnyOneSource")
  public void correctlyListDistinctQuestionsWithAtLeastOneYesFromMultipleAnswer(List<String> answer, int nbOfYesAnswers) {
    // Arrange
    GroupAnswerCollector groupAnswerCollector = new GroupAnswerCollector();

    // Act
    answer.forEach(currentAnswer -> groupAnswerCollector.collect(currentAnswer));

    // Assert
    Assertions.assertEquals(nbOfYesAnswers, groupAnswerCollector.getDistinctQuestionsWithYesAnswers().size());
  }

  @ParameterizedTest
  @MethodSource("multipleAnswerWithYesFromEveryoneSource")
  public void correctlyListDistinctQuestionsWithYesFromEveryoneFromMultipleAnswer(List<String> answer, int nbOfYesAnswers) {
    // Arrange
    GroupAnswerCollector groupAnswerCollector = new GroupAnswerCollector();

    // Act
    answer.forEach(currentAnswer -> groupAnswerCollector.collect(currentAnswer));

    // Assert
    Assertions.assertEquals(nbOfYesAnswers, groupAnswerCollector.getDistinctQuestionsWithYesAnswersFromEveryOne().size());
  }

  static Stream<String> invalidAnswersSource() {
    return Stream.of(
        null,
        "",
        "123",
        "1 hjkkl",
        "324",
        "ABC"
    );
  }

  static Stream<Arguments> singleAnswerWithYesFromAnyOneSource() {
    return Stream.of(
        Arguments.arguments("abcx", 4),
        Arguments.arguments("abc", 3)
    );
  }

  static Stream<Arguments> multipleAnswerWithYesFromAnyOneSource() {
    return Stream.of(
        Arguments.arguments(Arrays.asList("abcx", "abcy", "abcz"), 6),
        Arguments.arguments(Arrays.asList("a", "b", "c"), 3),
        Arguments.arguments(Arrays.asList("a", "a", "a"), 1)
    );
  }

  static Stream<Arguments> multipleAnswerWithYesFromEveryoneSource() {
    return Stream.of(
        Arguments.arguments(Arrays.asList("abc"), 3),
        Arguments.arguments(Arrays.asList("a", "b", "c"), 0),
        Arguments.arguments(Arrays.asList("ab", "ac"), 1),
        Arguments.arguments(Arrays.asList("b"), 1)
    );
  }

}
