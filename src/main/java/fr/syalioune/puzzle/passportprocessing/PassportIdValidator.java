package fr.syalioune.puzzle.passportprocessing;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class PassportIdValidator implements FieldValidator {

  public static final Predicate<String> VALID_PATTERN = Pattern.compile("\\d{9}").asMatchPredicate();

  @Override
  public boolean isValid(Passport passport) {
    return passport.getPassportId() != null && VALID_PATTERN.test(passport.getPassportId());
  }
}
