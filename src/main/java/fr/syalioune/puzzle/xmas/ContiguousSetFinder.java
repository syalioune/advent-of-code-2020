package fr.syalioune.puzzle.xmas;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class ContiguousSetFinder {

  public TreeSet<Double> find(List<Double> input, Double searchedNumber) {
    if(input == null || input.size() < 2) {
      throw new IllegalArgumentException();
    }
    List<Double> contiguousSet = new ArrayList<>();
    Double currentSum = 0.0;
    boolean found = false;
    int start = 0;
    while(!found && start < input.size()) {
      start++;
      for (int i = start; i < input.size() && !found; i++) {
        contiguousSet.add(input.get(i));
        currentSum += input.get(i);
        if (currentSum > searchedNumber) {
          contiguousSet = new ArrayList<>();
          currentSum = 0.0;
          break;
        } else if (currentSum.equals(searchedNumber)) {
          found = true;
        }
      }
    }
    return new TreeSet<>(contiguousSet);
  }
}
