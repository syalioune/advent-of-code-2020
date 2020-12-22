package fr.syalioune.puzzle.docking;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    try {
      List<String> lines = Files.readAllLines(Path.of("src/main/resources/docking-data-input.txt"));
      if(lines != null) {
        InitializationProgram program = new InitializationProgram();
        lines.forEach(line -> {
          if(line.startsWith("mask")) {
            String mask = line.split("=")[1].trim();
            program.setMask(mask);
          } else {
            String[] operands = line.split("=");
            Long value = Long.parseLong(operands[1].trim());
            String ad = operands[0].split("\\[")[1];
            Long address = Long.parseLong(ad.substring(0,ad.length()-2));
            program.setMemory(address, value);
          }
        });
        System.out.println(program.getMemoryTotal());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
