package fr.syalioune.puzzle.binaryboarding;

import fr.syalioune.puzzle.password.PasswordPolicyEnforcer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) {
    try {
      List<String> lines = Files.readAllLines(Path.of("src/main/resources/seat-binary-search-input.txt"));
      if(lines != null) {
        SeatFinder seatFinder = new SeatFinder();
        List<Integer> seatIds = lines.stream().map(line -> seatFinder.reset().find(line)).peek(s -> System.out.println(s)).sorted().collect(
            Collectors.toList());
        System.out.println("******************************");
        System.out.println("Highest seat id :" + seatIds.get(seatIds.size()-1));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
