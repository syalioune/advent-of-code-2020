package fr.syalioune.puzzle.password;

import fr.syalioune.puzzle.reportrepair.ReportRepair;
import fr.syalioune.puzzle.reportrepair.Tuple;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) {
    try {
      List<String> lines = Files.readAllLines(Path.of("src/main/resources/password-policy-input.txt"));
      if(lines != null) {
        PasswordPolicyEnforcer policyEnforcer = new PasswordPolicyEnforcer();
        long nbOfCorrectPasswords = lines.stream().filter(line -> policyEnforcer.isValid(line)).count();
        System.out.println(nbOfCorrectPasswords);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
