package fr.syalioune.puzzle.handyhaversacks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CapacityRuleParser {

  private static final Pattern VALID_PATTERN = Pattern.compile("(.+) bags contain (\\d+ (.+) bag[s]{0,1}[\\.|,]\\s*)+");

  private static final Predicate<String> IGNORE_PATTERN = Pattern.compile("(.+) bags contain no other bags.").asMatchPredicate();

  private static final Pattern BAG_EXTRACTOR = Pattern.compile("\\s*(\\d+) (.+) bag[s]{0,1}.*");

  private static final Predicate<String> VALID_PREDICATE = VALID_PATTERN.asMatchPredicate();

  public Map<String, Map<String, Integer>> parse(String rule) {
    Map<String, Map<String,Integer>> result = new HashMap<>();
    if (rule != null && IGNORE_PATTERN.test(rule)) {
      return result;
    } else if(rule == null || !VALID_PREDICATE.test(rule)) {
      throw new IllegalArgumentException();
    }
    Matcher matcher = VALID_PATTERN.matcher(rule);
    matcher.find();
    String outerBag = matcher.group(1);
    String innerBags = matcher.group(2);
    String[] bags = innerBags.split(",");
    for(String bag : bags) {
      Matcher match = BAG_EXTRACTOR.matcher(bag);
      match.find();
      Integer nbOfInnerBag = Integer.parseInt(match.group(1));
      String innerBag = match.group(2);
      result.merge(outerBag, Map.of(innerBag, nbOfInnerBag), (map1, map2) -> {
        Map<String, Integer> mergeMap = new HashMap<>(map1);
        map2.forEach((key, value) -> {
          mergeMap.merge(key, value, (a,b) -> a+b);
        });
        return mergeMap;
      });
    }
    return result;
  }

  public Map<String, Map<String, Integer>> parse(List<String> rules) {
    Map<String, Map<String, Integer>> result = parse(rules.get(0));
    for (int i = 1; i < rules.size(); i++) {
      Map<String, Map<String, Integer>> capacity = parse(rules.get(i));
      capacity.forEach((outerBag, innerBags) -> {
        result.merge(outerBag, innerBags, (map1, map2) -> {
          Map<String, Integer> mergeMap = new HashMap<>(map1);
          map2.forEach((key, value) -> {
            mergeMap.merge(key, value, (a,b) -> a+b);
          });
          return mergeMap;
        });
      });
    }
    return result;
  }
}
