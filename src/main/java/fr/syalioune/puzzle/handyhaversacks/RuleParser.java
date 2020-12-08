package fr.syalioune.puzzle.handyhaversacks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RuleParser {

  private static final Pattern VALID_PATTERN = Pattern.compile("(.+) bags contain (\\d+ (.+) bag[s]{0,1}[\\.|,]\\s*)+");

  private static final Predicate<String> IGNORE_PATTERN = Pattern.compile("(.+) bags contain no other bags.").asMatchPredicate();

  private static final Pattern BAG_EXTRACTOR = Pattern.compile("\\s*\\d+ (.+) bag[s]{0,1}.*");

  private static final Predicate<String> VALID_PREDICATE = VALID_PATTERN.asMatchPredicate();

  public Map<String, Set<String>> parse(String rule) {
    Map<String, Set<String>> result = new HashMap<>();
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
      Set<String> outerBags = new TreeSet<>();
      outerBags.add(outerBag);
      result.put(match.group(1), outerBags);
    }
    return result;
  }

  public Map<String, Set<String>> parse(List<String> rules) {
    Map<String, Set<String>> result = parse(rules.get(0));
    for (int i = 1; i < rules.size(); i++) {
      Map<String, Set<String>> affinity = parse(rules.get(i));
      affinity.forEach((innerBag, outerBags) -> {
        result.merge(innerBag, outerBags, (bags1, bags2) -> {
          bags1.addAll(bags2);
          return bags1;
        });
      });
    }
    return result;
  }
}
