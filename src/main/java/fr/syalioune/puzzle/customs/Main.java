package fr.syalioune.puzzle.customs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    try {
      List<String> lines = Files.readAllLines(Path.of("src/main/resources/group-answer-collector-input.txt"));
      if(lines != null) {
        List<String> groupLines = new LinkedList<>();
        int distinctAnswerSum = 0;
        GroupAnswerCollector groupAnswerCollector = new GroupAnswerCollector();
        for(String line : lines) {
          if(line.isEmpty() || line.isBlank()) {
            groupAnswerCollector.reset();
            groupLines.forEach(l -> groupAnswerCollector.collect(l));
            distinctAnswerSum += groupAnswerCollector.getDistinctQuestionsWithYesAnswers().size();
            groupLines = new LinkedList<>();
          } else {
            groupLines.add(line);
          }
        }
        if(groupLines.size() > 0){
          groupAnswerCollector.reset();
          groupLines.forEach(l -> groupAnswerCollector.collect(l));
          distinctAnswerSum += groupAnswerCollector.getDistinctQuestionsWithYesAnswers().size();
        }
        System.out.println("Sum : "+distinctAnswerSum);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
