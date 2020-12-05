package fr.syalioune.puzzle.passportprocessing;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class HeightValidatorShould {

  @ParameterizedTest
  @MethodSource("invalidPassportSource")
  public void invalidateThePassport(Passport passport) {
    // Arrange
    HeightValidator validator = new HeightValidator();

    // Act
    Boolean result = validator.isValid(passport);

    // Assert
    Assertions.assertFalse(result);
  }

  @ParameterizedTest
  @MethodSource("validPassportSource")
  public void validateThePassport(Passport passport) {
    // Arrange
    HeightValidator validator = new HeightValidator();

    // Act
    Boolean result = validator.isValid(passport);

    // Assert
    Assertions.assertTrue(result);
  }


  static Stream<Passport> invalidPassportSource() {
    return Stream.of(
        Passport.builder().build(),
        Passport.builder().withHeight("  ").build(),
        Passport.builder().withHeight("").build(),
        Passport.builder().withHeight("187").build(),
        Passport.builder().withHeight("140cm").build(),
        Passport.builder().withHeight("195cm").build(),
        Passport.builder().withHeight("50in").build(),
        Passport.builder().withHeight("80in").build()
    );
  }

  static Stream<Passport> validPassportSource() {
    return Stream.of(
        Passport.builder().withHeight("150cm").build(),
        Passport.builder().withHeight("155cm").build(),
        Passport.builder().withHeight("193cm").build(),
        Passport.builder().withHeight("59in").build(),
        Passport.builder().withHeight("70in").build(),
        Passport.builder().withHeight("76in").build()
    );
  }
}
