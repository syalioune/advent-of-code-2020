package fr.syalioune.puzzle.seating;

public class NeighbourHoodCalculator {

  private Ferry ferry;

  public NeighbourHoodCalculator(Ferry ferry) {
    this.ferry = ferry;
  }

  public int nbOfAdjacentOccupiedSeats(int x, int y) {
    int result = 0;
    for (int i = -1; i < 2 ; i++) {
      for (int j = -1; j < 2; j++) {
        if(i==0 && j==0) {
          continue;
        }
        int abs = x+i;
        int ord = y+j;
        if(abs >= 0 && abs < ferry.getHeight() && ord >= 0 && ord < ferry.getWidth() && ferry.get(abs,ord) == SeatState.OCCUPIED) {
          result++;
        }
      }
    }

    return result;
  }
}
