package fr.syalioune.puzzle.reportrepair;

import java.util.List;

/**
 * Report repair logic.
 */
public class ReportRepair {

  /**
   * Return the tuple from the expense list whose sum is the given expected number.
   *
   * @param expenses       Expense list
   * @param expectedNumber Expected number
   * @return
   */
  public Tuple findTuple(List<Integer> expenses, int expectedNumber) {
    Tuple result = Tuple.EMPTY;
    if (expenses != null && expenses.size() >= 2) {
      boolean found = false;
      for (int i = 0; i < expenses.size() && !found; i++) {
        Integer first = expenses.get(i);
        if (first != null && first < expectedNumber) {
          for (int j = 0; j != i && j < expenses.size(); j++) {
            Integer second = expenses.get(j);
            if (second != null && first + second == expectedNumber) {
              result = new Tuple(first, second);
              found = true;
              break;
            }
          }
        }
      }
    }
    return result;
  }
}
