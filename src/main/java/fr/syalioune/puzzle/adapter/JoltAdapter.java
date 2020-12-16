package fr.syalioune.puzzle.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class JoltAdapter {

  private static final Integer MAX_DIFFERENCE = 3;

  public Map<Integer, Integer> arrange(List<Integer> inputs) {
    if (inputs == null || inputs.size() < 1) {
      throw new IllegalArgumentException();
    }
    TreeSet<Integer> sortedList = new TreeSet<>(inputs);
    if (sortedList.first() > MAX_DIFFERENCE) {
      throw new IllegalArgumentException();
    }
    Map<Integer, Integer> distribution = new HashMap<>();
    distribution.put(3, 1);
    distribution.put(sortedList.first(), 1);
    List<Integer> sortedInput = sortedList.stream().collect(Collectors.toList());
    for (int i = 1; i < sortedInput.size(); i++) {
      int diff = sortedInput.get(i) - sortedInput.get(i - 1);
      if (diff > MAX_DIFFERENCE) {
        throw new IllegalArgumentException();
      } else {
        distribution.merge(diff, 1, (a, b) -> a + b);
      }
    }
    return distribution;
  }

  public long countArrangements(List<Integer> inputs) {
    if(inputs == null || inputs.size() < 1) {
      throw new IllegalArgumentException();
    }
    Map<Integer, Long> countCache = new HashMap<>();
    TreeSet<Integer> numbers = new TreeSet<>(inputs);
    long result = 0;
    for (int i = 1; i < 4 ; i++) {
      result += arrangement(i, numbers, countCache);
    }
    return result;
  }

  public long arrangement(int current, TreeSet<Integer> numbers, Map<Integer, Long> countCache) {
    if(current == numbers.last()) {
      return 1;
    } else if(numbers.contains(current)) {
      if(countCache.containsKey(current)) {
        return countCache.get(current);
      }
      long result = 0;
      for (int j = 1; j <= MAX_DIFFERENCE; j++) {
        int next = current + j;
        if(numbers.contains(next)) {
          result += arrangement(next, numbers, countCache);
        }
      }
      countCache.put(current, result);
      return result;
    } else {
      return 0;
    }
  }

}
