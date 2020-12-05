package fr.syalioune.puzzle.passportprocessing;

public class EyeColorValidator implements FieldValidator{

  @Override
  public boolean isValid(Passport password) {
    return password.getEyeColor() != null && !password.getEyeColor().isBlank() && !password.getEyeColor().isEmpty();
  }
}
