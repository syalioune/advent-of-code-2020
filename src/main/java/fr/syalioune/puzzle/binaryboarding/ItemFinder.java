package fr.syalioune.puzzle.binaryboarding;

import java.util.function.Predicate;

public abstract class ItemFinder {

  protected int lowerBound = 0;

  protected int upperBound = 0;

  protected int number = -1;

  ItemFinder(int i) {
    this.upperBound = i - 1;
  }

  ItemFinder() {

  }

  public void find(String rowDescription) {
    if(rowDescription == null || !getValidPattern().test(rowDescription)) {
      throw new IllegalArgumentException("The row description must match item regex");
    }
    for (int i = 0; i < rowDescription.length(); i++) {
      String order = rowDescription.substring(i, i+1);
      double intervalHalf = (double) lowerBound + ((double) (upperBound - lowerBound)) / 2.0;
      if(getFirstHalfLetter().equals(order)) {
        this.upperBound = (int) Math.floor(intervalHalf);
      } else {
        this.lowerBound = (int) Math.ceil(intervalHalf);
      }
    }
  }

  public int getItemNumber() {
    if(lowerBound == upperBound) {
      this.number = lowerBound;
      return number;
    }
    throw new IllegalStateException();
  }

  public int getSearchIntervalUpperBound() {
    return upperBound;
  }

  public int getSearchIntervalLowerBound() {
    return lowerBound;
  }

  public abstract Predicate<String> getValidPattern();

  public abstract String getFirstHalfLetter();

  public abstract String getLastHalfLetter();

}
