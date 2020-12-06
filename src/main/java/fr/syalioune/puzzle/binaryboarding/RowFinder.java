package fr.syalioune.puzzle.binaryboarding;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class RowFinder extends ItemFinder {

  private static final Predicate<String> VALID_PATTERN = Pattern.compile("(F|B){1,7}").asMatchPredicate();

  public RowFinder(int i) {
    super(i);
  }

  public RowFinder() {
    super();
  }

  @Override
  public Predicate<String> getValidPattern() {
    return VALID_PATTERN;
  }

  @Override
  public String getFirstHalfLetter() {
    return "F";
  }

  @Override
  public String getLastHalfLetter() {
    return "B";
  }
}
