package fr.syalioune.puzzle.docking;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class InitializationProgram {

  private static final Predicate<String> VALID_PATTERN = Pattern.compile("(X|0|1){36}").asMatchPredicate();

  private static final String DEFAULT_MASK = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";

  private String mask;

  private Map<Long, Long> memory = new HashMap<>();

  public InitializationProgram() {
    this.mask = DEFAULT_MASK;
  }
  
  public InitializationProgram(String mask) {
    this.mask = mask;
  }

  public Long applyMask(Long input) {
    if(mask == null || !VALID_PATTERN.test(mask)) {
      throw new IllegalArgumentException();
    }
    String inputBits = String.format("%1$"+mask.length()+"s", Long.toBinaryString(input)).replace(' ', '0');
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < mask.length(); i++) {
      String maskChar = mask.substring(i, i+1);
      String inputChar = inputBits.substring(i, i+1);
      if("X".equals(maskChar)) {
        sb.append(inputChar);
      } else {
        sb.append(maskChar);
      }
    }
    return Long.parseUnsignedLong(sb.toString(), 2);
  }

  public Long getMemory(Long address) {
    return memory.getOrDefault(address, 0L);
  }

  public void setMemory(Long address, Long number) {
    memory.put(address, applyMask(number));
  }

  public void setMask(String mask) {
    this.mask = mask;
  }

  public Long getMemoryTotal() {
    return memory.entrySet().stream().reduce(0L, (sum, entry) -> sum+entry.getValue(), (a,b) -> a+b);
  }
}
