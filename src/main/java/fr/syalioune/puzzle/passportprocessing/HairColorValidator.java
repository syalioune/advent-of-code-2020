package fr.syalioune.puzzle.passportprocessing;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class HairColorValidator implements FieldValidator{

  public static final Predicate<String> VALID_PATTERN = Pattern.compile("#(\\d|[a-f]){6}").asMatchPredicate();

  @Override
  public boolean isValid(Passport passport) {
    return passport.getHairColor() != null && VALID_PATTERN.test(passport.getHairColor());
  }
}
