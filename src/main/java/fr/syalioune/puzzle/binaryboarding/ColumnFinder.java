package fr.syalioune.puzzle.binaryboarding;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class ColumnFinder extends ItemFinder {

  private static final Predicate<String> VALID_PATTERN = Pattern.compile("(L|R){1,3}").asMatchPredicate();

  public ColumnFinder(int i) {
    super(i);
  }

  public ColumnFinder() {
    super();
  }

  @Override
  public Predicate<String> getValidPattern() {
    return VALID_PATTERN;
  }

  @Override
  public String getFirstHalfLetter() {
    return "L";
  }

  @Override
  public String getLastHalfLetter() {
    return "R";
  }
}
