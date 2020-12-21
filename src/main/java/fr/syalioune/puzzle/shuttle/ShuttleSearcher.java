package fr.syalioune.puzzle.shuttle;

import java.util.List;
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
}
