package fr.syalioune.puzzle.passportprocessing;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class EyeColorValidatorShould {

  @ParameterizedTest
  @MethodSource("invalidPassportSource")
  public void invalidateThePassport(Passport passport) {
    // Arrange
    EyeColorValidator validator = new EyeColorValidator();

    // Act
    Boolean result = validator.isValid(passport);

    // Assert
    Assertions.assertFalse(result);
  }

  @ParameterizedTest
  @MethodSource("validPassportSource")
  public void validateThePassport(Passport passport) {
    // Arrange
    EyeColorValidator validator = new EyeColorValidator();

    // Act
    Boolean result = validator.isValid(passport);

    // Assert
    Assertions.assertTrue(result);
  }


  static Stream<Passport> invalidPassportSource() {
    return Stream.of(
        Passport.builder().build(),
        Passport.builder().withEyeColor("  ").build(),
        Passport.builder().withEyeColor("").build(),
        Passport.builder().withEyeColor("gudesg").build()
    );
  }

  static Stream<Passport> validPassportSource() {
    return Stream.of(
        Passport.builder().withEyeColor("amb").build(),
        Passport.builder().withEyeColor("blu").build(),
        Passport.builder().withEyeColor("brn").build(),
        Passport.builder().withEyeColor("gry").build(),
        Passport.builder().withEyeColor("grn").build(),
        Passport.builder().withEyeColor("hzl").build(),
        Passport.builder().withEyeColor("oth").build()
    );
  }
}
