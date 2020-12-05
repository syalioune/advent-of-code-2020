package fr.syalioune.puzzle.password;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Password policy enforcer.
 */
public class PasswordPolicyEnforcer {

  public static final Predicate<String> VALID_PATTERN = Pattern.compile("\\d+\\-\\d+ [a-z]\\: .*").asMatchPredicate();

  /**
   * Check if a password is valid given a policy.
   *
   * @param input
   *          The validation input
   *
   * @return True if the password is valid, false otherwise.
   */
  public Boolean isValid(String input) {
    if (input != null && VALID_PATTERN.test(input)) {
      String[] parts = input.split(" ");
      char searchedChar = parts[1].charAt(0);
      long nbOfChar = 0;
      char[] password = parts[2].toCharArray();
      for(int i = 0; i < password.length ; i++) {
        if(password[i] == searchedChar) nbOfChar++;
      }
      return Long.parseLong(parts[0].split("-")[0]) <= nbOfChar && nbOfChar <= Long.parseLong(parts[0].split("-")[1]);
    } else {
      throw new IllegalArgumentException(
          "Bad format : the validation input must have the format \\d+\\-\\d+ [a-z]\\: .*");
    }
  }
}
