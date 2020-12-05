package fr.syalioune.puzzle.passportprocessing;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class BirthYearValidatorShould {

  @ParameterizedTest
  @MethodSource("invalidPassportSource")
  public void invalidateThePassport(Passport passport) {
    // Arrange
    BirthYearValidator validator = new BirthYearValidator();

    // Act
    Boolean result = validator.isValid(passport);

    // Assert
    Assertions.assertFalse(result);
  }

  @ParameterizedTest
  @MethodSource("validPassportSource")
  public void validateThePassport(Passport passport) {
    // Arrange
    BirthYearValidator validator = new BirthYearValidator();

    // Act
    Boolean result = validator.isValid(passport);

    // Assert
    Assertions.assertTrue(result);
  }


  static Stream<Passport> invalidPassportSource() {
    return Stream.of(
        Passport.builder().build(),
        Passport.builder().withBirthYear("  ").build(),
        Passport.builder().withBirthYear("").build(),
        Passport.builder().withBirthYear("ab").build(),
        Passport.builder().withBirthYear("20").build(),
        Passport.builder().withBirthYear("1900").build(),
        Passport.builder().withBirthYear("2003").build()
    );
  }

  static Stream<Passport> validPassportSource() {
    return Stream.of(
        Passport.builder().withBirthYear("1921").build(),
        Passport.builder().withBirthYear("1920").build(),
        Passport.builder().withBirthYear("2002").build()
    );
  }
}
