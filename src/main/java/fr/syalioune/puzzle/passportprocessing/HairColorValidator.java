package fr.syalioune.puzzle.passportprocessing;

public class HairColorValidator implements FieldValidator{

  @Override
  public boolean isValid(Passport password) {
    return password.getHairColor() != null && !password.getHairColor().isBlank() && !password.getHairColor().isEmpty();
  }
}
