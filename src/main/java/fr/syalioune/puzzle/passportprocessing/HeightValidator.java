package fr.syalioune.puzzle.passportprocessing;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class HeightValidator implements FieldValidator {

  public static final Predicate<String> VALID_PATTERN = Pattern.compile("\\d+cm|\\d+in").asMatchPredicate();

  @Override
  public boolean isValid(Passport passport) {
    if(passport.getHeight() == null || !VALID_PATTERN.test(passport.getHeight())) {
      return false;
    } else if(passport.getHeight().contains("cm")) {
      Long height = Long.parseLong(passport.getHeight().substring(0, passport.getHeight().length()-2));
      return height >= 150 && height <= 193;
    } else if(passport.getHeight().contains("in")) {
      Long height = Long.parseLong(passport.getHeight().substring(0, passport.getHeight().length()-2));
      return height >= 59 && height <= 76;
    } else {
      return false;
    }
  }
}
