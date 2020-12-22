package fr.syalioune.puzzle.shuttle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Main {

  public static void main(String[] args) {
    try {
      List<String> lines = Files.readAllLines(Path.of("src/main/resources/shuttle-search-input.txt"));
      if(lines != null) {
        List<Integer> busIds = new ArrayList<>();
        for(String input : lines.get(1).split(",")) {
          if(!"x".equals(input)) {
            busIds.add(Integer.parseInt(input));
          } else {
            busIds.add(-1);
          }
        }
        ShuttleSearcher shuttleSearcher = new ShuttleSearcher();
        long departure = shuttleSearcher.findDepartureTime(busIds);
        System.out.println(departure);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
