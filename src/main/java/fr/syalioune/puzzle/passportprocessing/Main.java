package fr.syalioune.puzzle.passportprocessing;

import fr.syalioune.puzzle.password.PasswordPolicyEnforcer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    try {
      List<String> lines = Files.readAllLines(Path.of("src/main/resources/passport-validator-input.txt"));
      if(lines != null) {
        List<String> passportLines = new LinkedList<>();
        List<Passport> passports = new LinkedList<>();
        for(String line : lines) {
          if(line.isEmpty() || line.isBlank()) {
            Passport passport = Passport.builder().withLines(passportLines).build();
            passports.add(passport);
            passportLines = new LinkedList<>();
          } else {
            passportLines.add(line);
          }
        }
        if(passportLines.size() > 0){
          Passport passport = Passport.builder().withLines(passportLines).build();
          passports.add(passport);
        }
        PassportValidator passportValidator = new PassportValidator(
            Arrays.asList(
                new BirthYearValidator(),
                new CountryIdValidator(),
                new ExpirationYearValidator(),
                new EyeColorValidator(),
                new HairColorValidator(),
                new HeightValidator(),
                new IssueYearValidator(),
                new PassportIdValidator()
            )
        );
        long validPassports = passports.stream().peek(p -> System.out.println(p)).filter(passport -> passportValidator.isValid(passport)).count();
        System.out.println("Passports : "+passports.size());
        System.out.println("Valid passports : "+validPassports);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
