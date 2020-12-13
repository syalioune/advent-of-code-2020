package fr.syalioune.puzzle.codeexecutor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    try {
      List<String> lines = Files.readAllLines(Path.of("src/main/resources/command-executor-input.txt"));
      if(lines != null) {
        Executor executor = new Executor(0);
        try {
          executor.runAndFix(lines);
          System.out.println("Run and Fix - Accumulator value : "+executor.getAccumulator());
          executor.run(lines);
        } catch (LoopException loopException) {
          System.out.println("Loop detected - Accumulator value : "+executor.getAccumulator());
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
