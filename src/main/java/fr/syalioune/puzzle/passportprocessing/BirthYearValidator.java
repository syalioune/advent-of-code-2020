package fr.syalioune.puzzle.passportprocessing;

public class BirthYearValidator implements  FieldValidator{

  @Override
  public boolean isValid(Passport password) {
    return password.getBirthYear() != null && !password.getBirthYear().isBlank() && !password.getBirthYear().isEmpty();
  }
}
