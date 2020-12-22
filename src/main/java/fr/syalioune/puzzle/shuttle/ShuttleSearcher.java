package fr.syalioune.puzzle.shuttle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class ShuttleSearcher {

  public BusDeparture findDeparture(int earliestTimestamp, List<Integer> busIds) {
    BusDeparture departure = new BusDeparture();
    if(busIds == null || busIds.size() == 0) {
      throw new IllegalArgumentException();
    }
    TreeSet<Integer> sortedIds = new TreeSet<>(busIds);
    if(sortedIds.first() < 0) {
      throw new IllegalArgumentException();
    }
    boolean found = false;
    for (int i = earliestTimestamp;  !found ; i++) {
      for(Integer busId : sortedIds) {
        if(i % busId == 0) {
          found = true;
          departure.setBusId(busId);
          departure.setTimestamp(i);
          break;
        }
      }
    }
    return departure;
  }

  /**
   * Find departure time using chinese remainder algorithm.
   */
  public Long findDepartureTime(List<Integer> busIds) {
    Long result = 0L;
    Map<Integer, Integer> ids = new HashMap<>();
    Long mod = 1L;
    for (int i = 0; i < busIds.size(); i++) {
      int num = busIds.get(i);
      if(num > 0) {
        ids.put(num - (i % num), num);
        mod *= num;
      }
    }
    for(Map.Entry<Integer,Integer> entry : ids.entrySet()) {
      result += entry.getKey() * remain(entry.getValue(), ids);
    }
    return result % mod;
  }

  private Long remain(Integer value, Map<Integer, Integer> ids) {
    Long result = 1L;
    Long invert = 1L;
    for(Map.Entry<Integer,Integer> entry : ids.entrySet()) {
      if(entry.getValue() != value) {
        invert *= entry.getValue();
      }
    }
    int coeff = 0;
    boolean found = false;
    while(!found) {
      coeff++;
      result = coeff * invert;
      found = result % value == 1;
    }
    return result;
  }
}
