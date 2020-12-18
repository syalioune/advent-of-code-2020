package fr.syalioune.puzzle.seating;

public enum SeatState {
  EMPTY("L"),
  OCCUPIED("#"),
  FLOOR(".");

  private String character;

  SeatState(String character) {
    this.character = character;
  }

  public static SeatState fromCharacter(String character) {
    SeatState state = EMPTY;
    switch (character) {
      case "L":
        state = EMPTY;
        break;
      case "#":
        state = OCCUPIED;
        break;
      case ".":
        state = FLOOR;
        break;
    }
    return state;
  }
}
