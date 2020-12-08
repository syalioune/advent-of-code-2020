package fr.syalioune.puzzle.handyhaversacks;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class MatchingBagFinder {

  private Map<String, Set<String>> bagAffinity;

  public MatchingBagFinder(Map<String, Set<String>> bagAffinity) {
    this.bagAffinity = new HashMap<>(bagAffinity);
  }

  public Set<String> find(String bagToMatch) {
    return find(bagToMatch, new HashMap<>());
  }

  private Set<String> find(String bagToMatch, Map<String, Integer> alreadyProcessedBags) {
    Set<String> result = new TreeSet<>();
    if(alreadyProcessedBags.containsKey(bagToMatch)) {
      return result;
    }
    if(bagAffinity.containsKey(bagToMatch)) {
      result.addAll(bagAffinity.get(bagToMatch));
      alreadyProcessedBags.put(bagToMatch, 0);
      List<String> secondIteration = new LinkedList<>();
      result.forEach(bag -> {
        if(!alreadyProcessedBags.containsKey(bag)) {
          secondIteration.addAll(find(bag, alreadyProcessedBags));
        }
      });
      result.addAll(secondIteration);
    }
    return result;
  }
}
