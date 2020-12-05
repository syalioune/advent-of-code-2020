package fr.syalioune.puzzle.passportprocessing;

public class IssueYearValidator implements FieldValidator {

  @Override
  public boolean isValid(Passport password) {
    return password.getIssueYear() != null && !password.getIssueYear().isBlank() && !password.getIssueYear().isEmpty();
  }
}
