package fr.syalioune.puzzle.treecounting;

import fr.syalioune.puzzle.reportrepair.ReportRepair;
import fr.syalioune.puzzle.reportrepair.Tuple;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) {
    try {
      List<String> lines = Files.readAllLines(Path.of("src/main/resources/tree-counting-input.txt"));
      if(lines != null) {
        char[][] map = new char[lines.size()][lines.get(0).length()];
        int i = 0;
        for(String line : lines) {
          int j = 0;
          for(j = 0; j < line.length(); j++) {
            map[i][j] = line.charAt(j);
          }
          i++;
        }
        TreeCounter treeCounter = new TreeCounter();
        long resultForSlope11 = treeCounter.count(map, new Slope(1,1));
        long resultForSlope31 = treeCounter.count(map, new Slope(3,1));
        long resultForSlope51 = treeCounter.count(map, new Slope(5,1));
        long resultForSlope71 = treeCounter.count(map, new Slope(7,1));
        long resultForSlope12 = treeCounter.count(map, new Slope(1,2));
        System.out.printf("%d %d %d %d %d\n", resultForSlope11, resultForSlope31, resultForSlope51, resultForSlope71, resultForSlope12);
        System.out.println(resultForSlope11*resultForSlope12*resultForSlope31*resultForSlope51*resultForSlope71);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
