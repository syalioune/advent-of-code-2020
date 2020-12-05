package fr.syalioune.puzzle.passportprocessing;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class PassportIdValidatorShould {

  @ParameterizedTest
  @MethodSource("invalidPassportSource")
  public void invalidateThePassport(Passport passport) {
    // Arrange
    PassportIdValidator validator = new PassportIdValidator();

    // Act
    Boolean result = validator.isValid(passport);

    // Assert
    Assertions.assertFalse(result);
  }

  @ParameterizedTest
  @MethodSource("validPassportSource")
  public void validateThePassport(Passport passport) {
    // Arrange
    PassportIdValidator validator = new PassportIdValidator();

    // Act
    Boolean result = validator.isValid(passport);

    // Assert
    Assertions.assertTrue(result);
  }


  static Stream<Passport> invalidPassportSource() {
    return Stream.of(
        Passport.builder().build(),
        Passport.builder().withPassportId("  ").build(),
        Passport.builder().withPassportId("").build(),
        Passport.builder().withPassportId("djla").build(),
        Passport.builder().withPassportId("0263").build(),
        Passport.builder().withPassportId("ade78").build()
    );
  }

  static Stream<Passport> validPassportSource() {
    return Stream.of(
        Passport.builder().withPassportId("000012345").build()
    );
  }
}
