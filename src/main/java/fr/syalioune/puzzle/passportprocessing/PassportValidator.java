package fr.syalioune.puzzle.passportprocessing;

import java.util.List;

public class PassportValidator {

  private List<FieldValidator> validators;

  public PassportValidator(List<FieldValidator> validators) {
    this.validators = validators;
  }

  public Boolean isValid(Passport passport) {
    return validators.stream().map(validator -> validator.isValid(passport)).reduce(true, (a,b) -> a && b);
  }
}
