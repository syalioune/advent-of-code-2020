package fr.syalioune.puzzle.reportrepair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) {
    try {
      List<String> lines = Files.readAllLines(Path.of("src/main/resources/input.txt"));
      if(lines != null) {
        List<Integer> expenses = lines.stream().map(line -> Integer.parseInt(line)).collect(
            Collectors.toList());
        ReportRepair report = new ReportRepair();
        Tuple result = report.findTuple(expenses, 2020);
        System.out.println(result);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
