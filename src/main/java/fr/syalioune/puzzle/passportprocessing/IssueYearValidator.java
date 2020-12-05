package fr.syalioune.puzzle.passportprocessing;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class IssueYearValidator implements FieldValidator {

  public static final Predicate<String> VALID_PATTERN = Pattern.compile("\\d{4}").asMatchPredicate();

  @Override
  public boolean isValid(Passport passport) {
    if(passport.getIssueYear() == null || !VALID_PATTERN.test(passport.getIssueYear())) {
      return false;
    }
    Long birthYear = Long.parseLong(passport.getIssueYear());
    return birthYear >= 2010 && birthYear <= 2020;
  }
}
