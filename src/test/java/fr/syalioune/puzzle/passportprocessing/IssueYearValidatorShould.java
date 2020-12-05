package fr.syalioune.puzzle.passportprocessing;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class IssueYearValidatorShould {

  @ParameterizedTest
  @MethodSource("invalidPassportSource")
  public void invalidateThePassport(Passport passport) {
    // Arrange
    IssueYearValidator validator = new IssueYearValidator();

    // Act
    Boolean result = validator.isValid(passport);

    // Assert
    Assertions.assertFalse(result);
  }

  @ParameterizedTest
  @MethodSource("validPassportSource")
  public void validateThePassport(Passport passport) {
    // Arrange
    IssueYearValidator validator = new IssueYearValidator();

    // Act
    Boolean result = validator.isValid(passport);

    // Assert
    Assertions.assertTrue(result);
  }


  static Stream<Passport> invalidPassportSource() {
    return Stream.of(
        Passport.builder().build(),
        Passport.builder().withIssueYear("  ").build(),
        Passport.builder().withIssueYear("").build(),
        Passport.builder().withIssueYear("ab").build(),
        Passport.builder().withIssueYear("20").build(),
        Passport.builder().withIssueYear("1900").build(),
        Passport.builder().withIssueYear("2040").build()
    );
  }

  static Stream<Passport> validPassportSource() {
    return Stream.of(
        Passport.builder().withIssueYear("2010").build(),
        Passport.builder().withIssueYear("2015").build(),
        Passport.builder().withIssueYear("2020").build()
    );
  }
}
