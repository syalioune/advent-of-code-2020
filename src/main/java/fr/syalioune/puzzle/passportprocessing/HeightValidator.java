package fr.syalioune.puzzle.passportprocessing;

public class HeightValidator implements FieldValidator {

  @Override
  public boolean isValid(Passport password) {
    return password.getHeight() != null && !password.getHeight().isBlank() && !password.getHeight().isEmpty();
  }
}
