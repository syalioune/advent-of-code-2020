package fr.syalioune.puzzle.seating;

public class Ferry {

  private SeatState[][] seats;

  public Ferry(SeatState[][] seats) {
    this.seats = seats;
  }

  public SeatState get(int x, int y) {
    return seats[x][y];
  }

  public int getWidth() {
    return seats[0].length;
  }

  public int getHeight() {
    return seats.length;
  }

}
