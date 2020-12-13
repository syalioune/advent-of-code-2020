package fr.syalioune.puzzle.codeexecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class Executor {

  public static final Predicate<String> VALID_PATTERN = Pattern.compile("(acc|nop|jmp) [+|-]\\d+").asMatchPredicate();

  private Map<Integer, Boolean> processedInstructions = new HashMap<>();

  private Map<Integer, Boolean> corruptionSuspects = new HashMap<>();

  private int accumulator = 0;

  private int instructionCapacity = 0;

  private int currentIndex = 1;

  public Executor(int i) {
    this.accumulator = i;
  }

  public Executor(int i, Integer instructionCapacity) {
    this.accumulator = i;
    this.instructionCapacity = instructionCapacity;
  }

  public void run(List<String> instructions) {
    reset();
    this.instructionCapacity = instructions.size();
    String currentCommand;
    while(true) {
      currentCommand = instructions.get(currentIndex-1);
      process(currentCommand);
    }
  }

  public void runAndFix(List<String> instructions) {
    reset();
    ArrayList<String> mutatedInstructions = new ArrayList<>(instructions);
    this.instructionCapacity = mutatedInstructions.size();
    String currentCommand;
    boolean loop = true;
    boolean mark = true;
    while(loop) {
      if(currentIndex == mutatedInstructions.size()+1) {
        break;
      }
      currentCommand = mutatedInstructions.get(currentIndex-1);
      if (mark) {
        if (currentCommand.startsWith("jmp") || currentCommand.startsWith("nop")) {
          corruptionSuspects.put(currentIndex - 1, false);
        }
      }
      try {
        process(currentCommand);
      } catch (LoopException | IllegalStateException loopException) {
        if(currentIndex == instructionCapacity + 1) {
          break;
        }
        mark = false;
        reset();
        mutatedInstructions = new ArrayList<>(instructions);
        instructionCapacity = mutatedInstructions.size();
        Optional<Entry<Integer, Boolean>> nextMutation = corruptionSuspects.entrySet().stream().filter(e -> !e.getValue()).findFirst();
        if(nextMutation.isPresent()) {
          corruptionSuspects.put(nextMutation.get().getKey(), true);
          String command = mutatedInstructions.get(nextMutation.get().getKey());
          if(command.startsWith("jmp")) {
            mutatedInstructions.set(nextMutation.get().getKey(), command.replace("jmp", "nop"));
          } else if(command.startsWith("nop")) {
            mutatedInstructions.set(nextMutation.get().getKey(), command.replace("nop", "jmp"));
          }
        } else {
          throw new LoopException();
        }
      }
    }
  }

  public void process(String instruction) {
    if(instruction == null || !VALID_PATTERN.test(instruction)) {
      throw new IllegalArgumentException();
    }
    processedInstructions.put(currentIndex, true);
    if(instruction.startsWith("nop")) {
      currentIndex++;
      return;
    } else if(instruction.startsWith("acc")) {
      Integer acc = Integer.parseInt(instruction.split(" ")[1]);
      accumulator += acc;
      currentIndex++;
    } else if(instruction.startsWith("jmp")) {
      Integer jump = Integer.parseInt(instruction.split(" ")[1]);
      this.currentIndex += jump;
      if(this.currentIndex < 1 || this.currentIndex > instructionCapacity) {
        throw new IllegalStateException();
      }
    }
    if(processedInstructions.containsKey(currentIndex)) {
      throw new LoopException();
    }
  }

  public Integer getAccumulator() {
    return accumulator;
  }

  public Integer getCurrentInstructionIndex() {
    return currentIndex;
  }

  private void reset() {
    this.accumulator = 0;
    this.instructionCapacity = 0;
    this.currentIndex = 1;
    this.processedInstructions = new HashMap<>();
  }
}
