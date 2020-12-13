package fr.syalioune.puzzle.xmas;

public class XmasEncodingException extends RuntimeException {

  private double badNumber;

  public XmasEncodingException(double number) {
    this.badNumber = number;
  }

  public double getNumber() {
    return badNumber;
  }
}
