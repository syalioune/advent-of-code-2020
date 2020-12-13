package fr.syalioune.puzzle.xmas;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) {
    try {
      List<String> lines = Files.readAllLines(Path.of("src/main/resources/xmas-encoding-error-input.txt"));
      if(lines != null) {
        XmasErrorDetector xmasErrorDetector = new XmasErrorDetector(25);
        ContiguousSetFinder finder = new ContiguousSetFinder();
        List<Double> numbers = lines.stream().map(line -> Double.parseDouble(line)).collect(
            Collectors.toList());
        try {
          xmasErrorDetector.scan(numbers);
        } catch (XmasEncodingException xmasEncodingException) {
          System.out.println("Invalid number : "+xmasEncodingException.getNumber());
          TreeSet<Double> sortedSet = finder.find(numbers, xmasEncodingException.getNumber());
          System.out.println(sortedSet.first() + " + " + sortedSet.last() + " = " + (sortedSet.last() + sortedSet.first()));
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
