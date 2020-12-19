package fr.syalioune.puzzle.ferry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    try {
      List<String> lines = Files.readAllLines(Path.of("src/main/resources/ferry-input.txt"));
      if(lines != null) {
        Ferry ferry = new Ferry();
        lines.forEach(line -> ferry.move(line));
        System.out.println(ferry.toString());
        System.out.println(Math.abs(ferry.getNorth()-ferry.getSouth())+Math.abs(ferry.getEast()-ferry
            .getWest()));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
