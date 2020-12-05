package fr.syalioune.puzzle.passportprocessing;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class EyeColorValidator implements FieldValidator{

  public static final Predicate<String> VALID_PATTERN = Pattern.compile("(amb|blu|brn|gry|grn|hzl|oth)").asMatchPredicate();

  @Override
  public boolean isValid(Passport passport) {
    return passport.getEyeColor() != null && VALID_PATTERN.test(passport.getEyeColor());
  }
}
