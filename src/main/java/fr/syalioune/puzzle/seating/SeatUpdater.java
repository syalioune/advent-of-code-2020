package fr.syalioune.puzzle.seating;

public class SeatUpdater {

  public SeatState nextState(SeatState state, int numberOfOccupiedAdjacentSeats) {
    if(state == SeatState.OCCUPIED && numberOfOccupiedAdjacentSeats >= 5) {
      return SeatState.EMPTY;
    } else if(state == SeatState.EMPTY && numberOfOccupiedAdjacentSeats == 0) {
      return SeatState.OCCUPIED;
    }
    return state;
  }
}
