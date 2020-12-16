package fr.syalioune.puzzle.adapter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) {
    try {
      List<String> lines = Files.readAllLines(Path.of("src/main/resources/adapter-array-input.txt"));
      if(lines != null) {
        JoltAdapter joltAdapter = new JoltAdapter();
        List<Integer> numbers = lines.stream().map(line -> Integer.parseInt(line)).collect(
            Collectors.toList());
        Map<Integer, Integer> distributions = joltAdapter.arrange(numbers);
        System.out.println(distributions.get(1)*distributions.get(3));
        long nbOfArrangements = joltAdapter.countArrangements(numbers);
        System.out.println(nbOfArrangements);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
