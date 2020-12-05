package fr.syalioune.puzzle.password;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class PasswordPolicyEnforcerShould {

  @ParameterizedTest
  @MethodSource("badFormattedPasswordValidationInputSource")
  public void rejectTheInputWhenItDoesNotRespectTheFormat(String input) {
    // Arrange
    PasswordPolicyEnforcer policyEnforcer = new PasswordPolicyEnforcer();

    // Act
    Assertions.assertThrows(IllegalArgumentException.class, () -> policyEnforcer.isValid(input));

    // Assert
    // Nothing to be done
  }

  @ParameterizedTest
  @MethodSource("validPasswordInput")
  public void validateThePasswordInput(String input) {
    // Arrange
    PasswordPolicyEnforcer policyEnforcer = new PasswordPolicyEnforcer();

    // Act
    Boolean result = policyEnforcer.isValid(input);

    // Assert
    Assertions.assertTrue(result);
  }

  @ParameterizedTest
  @MethodSource("invalidPasswordInput")
  public void invalidateThePasswordInput(String input) {
    // Arrange
    PasswordPolicyEnforcer policyEnforcer = new PasswordPolicyEnforcer();

    // Act
    Boolean result = policyEnforcer.isValid(input);

    // Assert
    Assertions.assertFalse(result);
  }

  static Stream<String> badFormattedPasswordValidationInputSource() {
    return Stream.of(
        null,
        "",
        "abcd",
        "1 hjkkl",
        "a-b 2:"
    );
  }

  static Stream<String> validPasswordInput() {
    return Stream.of(
        "1-3 a: abcde",
        "2-10 c: ccccccccc"
    );
  }

  static Stream<String> invalidPasswordInput() {
    return Stream.of(
        "1-3 b: cdefg"
    );
  }

}
