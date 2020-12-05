package fr.syalioune.puzzle.passportprocessing;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class PassportValidatorShould {

  @ParameterizedTest
  @MethodSource("invalidFieldValidatorSource")
  public void invalidateThePassport(List<FieldValidator> validators) {
    // Arrange
    Passport passport = Passport.builder().build();
    PassportValidator passportValidator = new PassportValidator(validators);

    // Act
    Boolean result = passportValidator.isValid(passport);

    // Assert
    Assertions.assertFalse(result);
  }

  @ParameterizedTest
  @MethodSource("validFieldValidatorSource")
  public void validateThePassport(List<FieldValidator> validators) {
    // Arrange
    Passport passport = Passport.builder().build();
    PassportValidator passportValidator = new PassportValidator(validators);

    // Act
    Boolean result = passportValidator.isValid(passport);

    // Assert
    Assertions.assertTrue(result);
  }

  static Stream<Arguments> invalidFieldValidatorSource() {
    return Stream.of(
        Arguments.arguments(Arrays.asList(new FieldValidator() {
          @Override
          public boolean isValid(Passport password) {
            return false;
          }
        })),
        Arguments.arguments(Arrays.asList(new FieldValidator() {
          @Override
          public boolean isValid(Passport password) {
            return true;
          }
        }, new FieldValidator() {
          @Override
          public boolean isValid(Passport password) {
            return false;
          }
        }))
    );
  }

  static Stream<Arguments> validFieldValidatorSource() {
    return Stream.of(
        Arguments.arguments(Arrays.asList(new FieldValidator() {
          @Override
          public boolean isValid(Passport password) {
            return true;
          }
        }), new FieldValidator() {
          @Override
          public boolean isValid(Passport password) {
            return true;
          }
        })
    );
  }
}
