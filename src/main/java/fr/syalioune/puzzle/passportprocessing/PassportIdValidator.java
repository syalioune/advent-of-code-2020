package fr.syalioune.puzzle.passportprocessing;

public class PassportIdValidator implements FieldValidator {

  @Override
  public boolean isValid(Passport password) {
    return password.getPassportId() != null && !password.getPassportId().isBlank() && !password.getPassportId().isEmpty();
  }
}
