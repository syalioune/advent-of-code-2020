package fr.syalioune.puzzle.passportprocessing;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class ExpirationYearValidator implements  FieldValidator {

  public static final Predicate<String> VALID_PATTERN = Pattern.compile("\\d{4}").asMatchPredicate();

  @Override
  public boolean isValid(Passport passport) {
    if(passport.getExpirationYear() == null || !VALID_PATTERN.test(passport.getExpirationYear())) {
      return false;
    }
    Long birthYear = Long.parseLong(passport.getExpirationYear());
    return birthYear >= 2020 && birthYear <= 2030;
  }
}
