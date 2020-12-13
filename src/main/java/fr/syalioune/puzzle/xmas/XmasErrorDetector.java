package fr.syalioune.puzzle.xmas;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class XmasErrorDetector {

  private int preambleSize = 0;

  public XmasErrorDetector(int preambleSize) {
    this.preambleSize = preambleSize;
  }

  public void scan(List<Double> input) {
    if(input == null || input.size() <= preambleSize) {
      throw new IllegalArgumentException("No null list or list with size below preamble one allowed");
    }
    for (int i = preambleSize; i < input.size(); i++) {
      int index = i-preambleSize;
      List<Double> preamble = input.subList(index, preambleSize + index);
      double numberUnderTest = input.get(i);
      boolean found = false;
      for (int j = 0; j < preamble.size() && !found; j++) {
        for (int k = 0; k != j && k < preamble.size() && !found ; k++) {
          found = numberUnderTest == (preamble.get(j)+preamble.get(k));
          if(found) {
            System.out.println(numberUnderTest + " = "+ preamble.get(j) + " + " + preamble.get(k));
          }
        }
      }
      if(!found) {
        throw new XmasEncodingException(numberUnderTest);
      }
    }
  }
}
