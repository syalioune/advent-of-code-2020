package fr.syalioune.puzzle.passportprocessing;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ExpirationYearValidatorShould {

  @ParameterizedTest
  @MethodSource("invalidPassportSource")
  public void invalidateThePassport(Passport passport) {
    // Arrange
    ExpirationYearValidator validator = new ExpirationYearValidator();

    // Act
    Boolean result = validator.isValid(passport);

    // Assert
    Assertions.assertFalse(result);
  }

  @ParameterizedTest
  @MethodSource("validPassportSource")
  public void validateThePassport(Passport passport) {
    // Arrange
    ExpirationYearValidator validator = new ExpirationYearValidator();

    // Act
    Boolean result = validator.isValid(passport);

    // Assert
    Assertions.assertTrue(result);
  }


  static Stream<Passport> invalidPassportSource() {
    return Stream.of(
        Passport.builder().build(),
        Passport.builder().withExpirationYear("  ").build(),
        Passport.builder().withExpirationYear("").build()
    );
  }

  static Stream<Passport> validPassportSource() {
    return Stream.of(
        Passport.builder().withExpirationYear("e").build()
    );
  }
}
