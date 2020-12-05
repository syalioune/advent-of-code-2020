package fr.syalioune.puzzle.passportprocessing;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class CountryIdValidatorShould {

  @ParameterizedTest
  @MethodSource("passportSource")
  public void alwaysPalidateThePassport(Passport passport) {
    // Arrange
    CountryIdValidator validator = new CountryIdValidator();

    // Act
    Boolean result = validator.isValid(passport);

    // Assert
    Assertions.assertTrue(result);
  }


  static Stream<Passport> passportSource() {
    return Stream.of(
        Passport.builder().build(),
        Passport.builder().withCountryId("  ").build(),
        Passport.builder().withCountryId("").build(),
        Passport.builder().withCountryId("c").build()
    );
  }
}
