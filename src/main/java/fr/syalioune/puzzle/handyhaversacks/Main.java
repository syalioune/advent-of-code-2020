package fr.syalioune.puzzle.handyhaversacks;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {

  public static void main(String[] args) {
    try {
      List<String> lines = Files.readAllLines(Path.of("src/main/resources/handy-haversacks-input.txt"));
      if(lines != null) {
        AffinityRuleParser affinityRuleParser = new AffinityRuleParser();
        CapacityRuleParser capacityRuleParser = new CapacityRuleParser();
        Map<String, Set<String>> bagAffinities = affinityRuleParser.parse(lines);
        Map<String, Map<String, Integer>> bagCapacities = capacityRuleParser.parse(lines);
        MatchingBagFinder matchingBagFinder = new MatchingBagFinder(bagAffinities);
        CapacityCalculator capacityCalculator = new CapacityCalculator(bagCapacities);
        Integer capacity = capacityCalculator.contain("shiny gold");
        Set<String> matchingBags = matchingBagFinder.find("shiny gold");
        matchingBags.forEach(bag -> {
          System.out.println(bag);
        });
        System.out.println(matchingBags.size());
        System.out.println(capacity);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
