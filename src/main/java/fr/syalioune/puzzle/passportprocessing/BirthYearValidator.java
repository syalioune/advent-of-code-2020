package fr.syalioune.puzzle.passportprocessing;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class BirthYearValidator implements FieldValidator {

  public static final Predicate<String> VALID_PATTERN = Pattern.compile("\\d{4}").asMatchPredicate();

  @Override
  public boolean isValid(Passport passport) {
    if(passport.getBirthYear() == null || !VALID_PATTERN.test(passport.getBirthYear())) {
      return false;
    }
    Long birthYear = Long.parseLong(passport.getBirthYear());
    return birthYear >= 1920 && birthYear <= 2002;
  }
}
