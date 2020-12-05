package fr.syalioune.puzzle.passportprocessing;

public class ExpirationYearValidator implements  FieldValidator {

  @Override
  public boolean isValid(Passport password) {
    return password.getExpirationYear() != null && !password.getExpirationYear().isBlank() && !password.getExpirationYear().isEmpty();
  }
}
