package fr.syalioune.puzzle.ticket;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TicketTranslator {

  private static final Pattern VALID_PATTERN = Pattern.compile("(\\d+)-(\\d+) or (\\d+)-(\\d+)");

  private static final Predicate<String> VALID_PREDICATE = VALID_PATTERN.asMatchPredicate();

  private Map<String, String> valueRanges = new HashMap<>();

  private Map<String, Predicate<Integer>> valuePredicates = new HashMap<>();

  public TicketTranslator(Map<String,String> valueRanges) {
    if(valueRanges == null || valueRanges.size() == 0) {
      throw new IllegalArgumentException("Empty or null range map");
    }
    valueRanges.forEach((field, range) -> {
      if(!isValidRange(range)) {
        throw new IllegalArgumentException(("Invalid range"));
      }
      this.valueRanges.put(field, range);
      this.valuePredicates.put(field, buildPredicate(range));
    });
  }

  private Predicate<Integer> buildPredicate(String range) {
    Matcher matcher = VALID_PATTERN.matcher(range);
    matcher.find();
    Predicate<Integer> firstPredicate = (num) -> num >= Integer.valueOf(matcher.group(1));
    firstPredicate = firstPredicate.and((num) -> num <= Integer.valueOf(matcher.group(2)));
    Predicate<Integer> secondPredicate = (num) -> num >= Integer.valueOf(matcher.group(3));
    secondPredicate = secondPredicate.and((num) -> num <= Integer.valueOf(matcher.group(4)));
    return firstPredicate.or(secondPredicate);
  }

  private boolean isValidRange(String range) {
   return range != null && VALID_PREDICATE.test(range);
  }

  public boolean isValid(Integer value) {
    return valuePredicates.values().stream().map(predicate -> predicate.test(value)).reduce(false, (a,b) -> a || b);
  }


}
