package fr.syalioune.puzzle.handyhaversacks;

import java.util.Collections;
import java.util.Map;

public class CapacityCalculator {

  Map<String, Map<String, Integer>> bagCapacity = Collections.emptyMap();

  public CapacityCalculator(Map<String, Map<String, Integer>> bagCapacity) {
    this.bagCapacity = bagCapacity;
  }

  public Integer contain(String bag) {
    if(bagCapacity.containsKey(bag)) {
      return bagCapacity.get(bag).entrySet().stream().map(entry -> entry.getValue() + entry.getValue() * contain(entry.getKey())).reduce(0, (a,b) -> a+b);
    } else {
      return 0;
    }
  }
}
