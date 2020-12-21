package fr.syalioune.puzzle.shuttle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    try {
      List<String> lines = Files.readAllLines(Path.of("src/main/resources/shuttle-search-input.txt"));
      if(lines != null) {
        Integer earliestTimestamp = Integer.parseInt(lines.get(0));
        List<Integer> busIds = new ArrayList<>();
        for(String input : lines.get(1).split(",")) {
          if(!"x".equals(input)) {
            busIds.add(Integer.parseInt(input));
          }
        }
        ShuttleSearcher shuttleSearcher = new ShuttleSearcher();
        BusDeparture departure = shuttleSearcher.findDeparture(earliestTimestamp, busIds);
        System.out.println(departure);
        System.out.println(departure.getBusId()*(departure.getTimestamp()-earliestTimestamp));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
