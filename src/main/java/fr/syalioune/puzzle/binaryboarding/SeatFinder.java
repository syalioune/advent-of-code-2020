package fr.syalioune.puzzle.binaryboarding;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class SeatFinder {

  private static final Predicate<String> VALID_PATTERN = Pattern.compile("(F|B){7}(L|R){3}").asMatchPredicate();

  private RowFinder rowFinder;

  private ColumnFinder columnFinder;

  private int row = 0;

  private int column = 0;

  public SeatFinder() {
    this.rowFinder = new RowFinder(128);
    this.columnFinder = new ColumnFinder(8);
  }

  public int find(String seatDescription) {
    if(seatDescription == null || !VALID_PATTERN.test(seatDescription)) {
      throw new IllegalArgumentException("The input must match (F|B){7}(L|R){3} regexp");
    }
    rowFinder.find(seatDescription.substring(0,7));
    row = rowFinder.getItemNumber();
    columnFinder.find(seatDescription.substring(7));
    column = columnFinder.getItemNumber();
    return getSeatId();
  }

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return column;
  }

  public int getSeatId() {
    return row * 8 + column;
  }

  public SeatFinder reset() {
    this.rowFinder = new RowFinder(128);
    this.columnFinder = new ColumnFinder(8);
    this.row = 0;
    this.column = 0;
    return this;
  }
}
