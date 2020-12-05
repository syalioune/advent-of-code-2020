package fr.syalioune.puzzle.passportprocessing;

import java.util.List;

public class Passport {

  private String birthYear;

  private String issueYear;

  private String expirationYear;

  private String height;

  private String hairColor;

  private String eyeColor;

  private String passportId;

  private String  countryId;

  private Passport(String birthYear, String issueYear, String expirationYear, String height,
      String hairColor, String eyeColor, String passportId, String countryId) {
    this.birthYear = birthYear;
    this.issueYear = issueYear;
    this.expirationYear = expirationYear;
    this.height = height;
    this.hairColor = hairColor;
    this.eyeColor = eyeColor;
    this.passportId = passportId;
    this.countryId = countryId;
  }

  public String getBirthYear() {
    return birthYear;
  }

  public String getIssueYear() {
    return issueYear;
  }

  public String getExpirationYear() {
    return expirationYear;
  }

  public String getHeight() {
    return height;
  }

  public String getHairColor() {
    return hairColor;
  }

  public String getEyeColor() {
    return eyeColor;
  }

  public String getPassportId() {
    return passportId;
  }

  public String getCountryId() {
    return countryId;
  }

  public static PassportBuilder builder() {
    return new PassportBuilder();
  }

  @Override
  public String toString() {
    return "Passport{" +
        "birthYear='" + birthYear + '\'' +
        ", issueYear='" + issueYear + '\'' +
        ", expirationYear='" + expirationYear + '\'' +
        ", height='" + height + '\'' +
        ", hairColor='" + hairColor + '\'' +
        ", eyeColor='" + eyeColor + '\'' +
        ", passportId='" + passportId + '\'' +
        ", countryId='" + countryId + '\'' +
        '}';
  }

  static class PassportBuilder {
    private String birthYear;

    private String issueYear;

    private String expirationYear;

    private String height;

    private String hairColor;

    private String eyeColor;

    private String passportId;

    private String  countryId;

    private PassportBuilder() {

    }

    public PassportBuilder withBirthYear(String birthYear) {
      this.birthYear = birthYear;
      return this;
    }

    public PassportBuilder withIssueYear(String issueYear) {
      this.issueYear = issueYear;
      return this;
    }

    public PassportBuilder withExpirationYear(String expirationYear) {
      this.expirationYear = expirationYear;
      return this;
    }

    public PassportBuilder withHeight(String height) {
      this.height = height;
      return this;
    }

    public PassportBuilder withHairColor(String hairColor) {
      this.hairColor = hairColor;
      return this;
    }

    public PassportBuilder withEyeColor(String eyeColor) {
      this.eyeColor = eyeColor;
      return this;
    }

    public PassportBuilder withPassportId(String passportId) {
      this.passportId = passportId;
      return this;
    }

    public PassportBuilder withCountryId(String countryId) {
      this.countryId = countryId;
      return this;
    }

    public PassportBuilder withLines(List<String> lines) {
      for(String line : lines) {
        String[] parts = line.split(" ");
        for(String part : parts) {
          String[] kv = part.split(":");
          switch (kv[0]) {
            case "byr":
              this.birthYear = kv[1];
              break;
            case "iyr":
              this.issueYear = kv[1];
              break;
            case "eyr":
              this.expirationYear = kv[1];
              break;
            case "hgt":
              this.height = kv[1];
              break;
            case "hcl":
              this.hairColor = kv[1];
              break;
            case "ecl":
              this.eyeColor = kv[1];
              break;
            case "pid":
              this.passportId = kv[1];
              break;
            case "cid":
              this.countryId = kv[1];
              break;
          }
        }
      }
      return this;
    }

    public Passport build() {
      return new Passport(birthYear, issueYear, expirationYear, height,
          hairColor, eyeColor, passportId, countryId);
    }
  }
}
