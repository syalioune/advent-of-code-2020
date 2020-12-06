package fr.syalioune.puzzle.customs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GroupAnswerCollector {

  private static final Predicate<String> VALID_PATTERN = Pattern.compile("[a-z]{1,26}").asMatchPredicate();

  private Map<String, Integer> answers = new HashMap<>();

  private int nbOfCollectAnswers = 0;

  public List<String> collect(String answer) {
    nbOfCollectAnswers++;
    if(answer == null || !VALID_PATTERN.test(answer)) {
      throw new IllegalArgumentException("The answer must match [a-z]{1,26} pattern : "+answer);
    }
    answer.chars().mapToObj(c -> Character.toString(c)).forEach(question -> {
      answers.merge(question, 1, (x,y) -> x+y);
    });
    return getDistinctQuestionsWithYesAnswers();
  }

  public List<String> getDistinctQuestionsWithYesAnswers() {
    return new LinkedList<>(answers.keySet());
  }
  
  public GroupAnswerCollector reset() {
    this.answers = new HashMap<>();
    this.nbOfCollectAnswers = 0;
    return this;
  }

  public List<String> getDistinctQuestionsWithYesAnswersFromEveryOne() {
    return answers.entrySet().stream().filter(e -> e.getValue() == nbOfCollectAnswers).map(e -> e.getKey()).collect(
        Collectors.toList());
  }
}
