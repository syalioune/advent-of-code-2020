package fr.syalioune.puzzle.ticket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javoa.util.Map.Entry;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

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


  public Map<String, Set<Integer>> fieldCandidates(List<List<Integer>> tickets) {
    Map<String,Set<Integer>> candidates = new HashMap<>();
    valueRanges.keySet().forEach(key -> {
      Set<Integer> set = new HashSet<>();
      IntStream.range(1,tickets.get(0).size()+1).boxed().forEach(index -> set.add(index));
      candidates.put(key, set);
    });
    for(List<Integer> ticket : tickets) {
      if(!ticket.stream().map(v -> isValid(v)).reduce(true,(a,b) -> a && b)) {
        continue;
      }
      for (int i = 0; i < ticket.size(); i++) {
        Integer value = ticket.get(i);
        int index = i+1;
        candidates.keySet().forEach(field -> {
          Predicate<Integer> predicate = valuePredicates.get(field);
          if(!predicate.test(value)) {
            candidates.get(field).remove(index);
          }
        });
      }
    }
    return candidates;
  }

  public Map<String, Integer> definitiveCandidates(Map<String, Set<Integer>> possibleCandidates) {
    Map<String,Integer> definitiveCandidates = new HashMap<>();
    List<Entry<String, Set<Integer>>> list = new ArrayList<>(possibleCandidates.entrySet());
    list.sort((o1, o2) -> o1.getValue().size() - o2.getValue().size());
    Set<Integer> alreadyChosen = new HashSet<>();
    for(Entry<String,Set<Integer>> entry : list) {
      List<Integer> l = new ArrayList<>(entry.getValue());
      if(entry.getValue().size() == 1) {
        Integer value = l.get(0);
        if(alreadyChosen.contains(value)) {
          throw new IllegalStateException();
        }
        definitiveCandidates.put(entry.getKey(), value);
        alreadyChosen.add(value);
      } else {
        Collections.sort(l);
        for(Integer value : l) {
          if(!alreadyChosen.contains(value)) {
            definitiveCandidates.put(entry.getKey(), value);
            alreadyChosen.add(value);
            break;
          }
        }
      }
    }
    return definitiveCandidates;
  }
}
