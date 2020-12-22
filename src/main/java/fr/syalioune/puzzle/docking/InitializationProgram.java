package fr.syalioune.puzzle.docking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

  public Long applyValueMask(Long input) {
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

  public void setMemoryV1(Long address, Long number) {
    memory.put(address, applyValueMask(number));
  }

  public void setMask(String mask) {
    this.mask = mask;
  }

  public Long getMemoryTotal() {
    return memory.entrySet().stream().reduce(0L, (sum, entry) -> sum+entry.getValue(), (a,b) -> a+b);
  }

  public List<Long> applyAddressMask(Long address) {
    if(mask == null || !VALID_PATTERN.test(mask)) {
      throw new IllegalArgumentException();
    }
    String inputBits = String.format("%1$"+mask.length()+"s", Long.toBinaryString(address)).replace(' ', '0');
    List<StringBuilder> sbs = new LinkedList<>();
    sbs.add(new StringBuilder());
    for (int i = 0; i < mask.length(); i++) {
      String maskChar = mask.substring(i, i+1);
      String inputChar = inputBits.substring(i, i+1);
      if("X".equals(maskChar)) {
        List<StringBuilder> newSbs = new LinkedList<>();
        sbs.forEach(sb -> {
          StringBuilder s = new StringBuilder(sb.toString());
          sb.append("0");
          s.append("1");
          newSbs.add(s);
        });
        sbs.addAll(newSbs);
      } else if ("0".equals(maskChar)) {
        sbs.forEach(sb -> sb.append(inputChar));
      } else {
        sbs.forEach(sb -> sb.append(maskChar));
      }
    }
    return sbs.stream().map(sb -> Long.parseUnsignedLong(sb.toString(), 2)).collect(Collectors.toList());
  }

  public void setMemoryV2(Long address, Long value) {
    List<Long> addresses = applyAddressMask(address);
    addresses.forEach(ad -> {
      memory.put(ad, value);
    });
  }
}
