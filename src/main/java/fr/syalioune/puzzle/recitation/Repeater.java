package fr.syalioune.puzzle.recitation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repeater {

  private List<Integer> startingNumbers;

  private List<Integer> spokenNumbers = new ArrayList<>();

  private Map<Integer, IndexPair> lastSpokenTurn = new HashMap<>();

  public Repeater(List<Integer> startingNumbers) {
    if(startingNumbers == null || startingNumbers.size() == 0) {
      throw new IllegalArgumentException();
    }
    this.startingNumbers = new ArrayList<>(startingNumbers);
    this.spokenNumbers = new ArrayList<>(startingNumbers);
    for (int i = 0; i < this.spokenNumbers.size(); i++) {
      IndexPair pair = new IndexPair();
      pair.addIndex(i+1);
      this.lastSpokenTurn.put(this.spokenNumbers.get(i), pair);
    }
  }

  public Integer spokenNumber(Integer turnId) {
    if(turnId <= 0) {
      throw new IllegalArgumentException();
    } else if (turnId <= spokenNumbers.size()) {
      return spokenNumbers.get(turnId-1);
    } else {
      Integer result = -1;
      for (int i = spokenNumbers.size(); i < turnId ; i++) {
        Integer previousNumber = spokenNumbers.get(i-1);
        IndexPair lastSpokenPair = lastSpokenTurn.get(previousNumber);
        if(lastSpokenPair.firstTime()) {
          result = 0;
        } else {
          result = lastSpokenPair.diff();
        }
        IndexPair resultPair = lastSpokenTurn.get(result);
        if(resultPair == null) {
          resultPair = new IndexPair();
          lastSpokenTurn.put(result, resultPair);
        }
        resultPair.addIndex(i+1);
        spokenNumbers.add(result);
      }
      return result;
    }
  }

  static class IndexPair {
    private Integer first = -1;

    private Integer second = -1;

    public void addIndex(Integer index) {
      if(first == -1) {
        first = index;
      }else if(second == -1) {
        second = index;
      } else {
        first = second;
        second = index;
      }
    }

    public boolean firstTime() {
      return second == -1;
    }

    public Integer diff() {
      return second - first;
    }
  }
}
