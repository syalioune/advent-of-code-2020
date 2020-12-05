package fr.syalioune.puzzle.passportprocessing;

public class CountryIdValidator implements  FieldValidator{

  @Override
  public boolean isValid(Passport passport) {
    return true;
  }
}
