package fr.syalioune.puzzle.seating;

public class NeighbourHoodCalculator {

  private Ferry ferry;

  public NeighbourHoodCalculator(Ferry ferry) {
    this.ferry = ferry;
  }

  public int nbOfAdjacentOccupiedSeats(int x, int y) {
    int result = 0;

    result += nbRightAdjacentOccupiedSeats(x, y);

    result += nbLeftAdjacentOccupiedSeats(x, y);

    result += nbBottomAdjacentOccupiedSeats(x, y);

    result += nbTopAdjacentOccupiedSeats(x, y);

    result += nbBottomRightDiagAdjacentOccupiedSeats(x, y);

    result += nbUpRightDiagAdjacentOccupiedSeats(x, y);

    result += nbBottomLeftDiagAdjacentOccupiedSeats(x, y);

    result += nbUpLeftDiagAdjacentOccupiedSeats(x, y);

    return result;
  }

  private int nbUpLeftDiagAdjacentOccupiedSeats(int x, int y) {
    int result = 0;
    for (int i = 1; ; i++) {
      int abs = x - i;
      int ord = y + i;
      if(abs >= 0 && ord < ferry.getWidth() && ferry.get(abs,ord) == SeatState.OCCUPIED ) {
        return 1;
      } else if(abs >= 0 && ord < ferry.getWidth() && ferry.get(abs,ord) == SeatState.EMPTY ) {
        return 0;
      } else if (abs < 0 || ord >= ferry.getWidth()) {
        return result;
      }
    }
  }

  private int nbBottomLeftDiagAdjacentOccupiedSeats(int x, int y) {
    int result = 0;
    for (int i = 1; ; i++) {
      int abs = x + i;
      int ord = y - i;
      if(abs < ferry.getHeight() && ord >= 0 && ferry.get(abs,ord) == SeatState.OCCUPIED ) {
        return 1;
      } else if(abs < ferry.getHeight() && ord >= 0 && ferry.get(abs,ord) == SeatState.EMPTY ) {
        return result;
      } else if (abs >= ferry.getHeight() || ord < 0) {
        return result;
      }
    }
  }

  private int nbUpRightDiagAdjacentOccupiedSeats(int x, int y) {
    int result = 0;
    for (int i = 1; ; i++) {
      int abs = x - i;
      int ord = y - i;
      if(abs >= 0 && ord >= 0 && ferry.get(abs,ord) == SeatState.OCCUPIED ) {
        return 1;
      } else if(abs >= 0 && ord >= 0 && ferry.get(abs,ord) == SeatState.EMPTY ) {
        return result;
      } else if (abs < 0 || ord < 0) {
        return result;
      }
    }
  }

  private int nbBottomRightDiagAdjacentOccupiedSeats(int x, int y) {
    int result = 0;
    for (int i = 1; ; i++) {
      int abs = x + i;
      int ord = y + i;
      if(abs < ferry.getHeight() && ord < ferry.getWidth() && ferry.get(abs,ord) == SeatState.OCCUPIED ) {
        return 1;
      } else if(abs < ferry.getHeight() && ord < ferry.getWidth() && ferry.get(abs,ord) == SeatState.EMPTY ) {
        return result;
      } else if (abs >= ferry.getHeight() || ord >= ferry.getWidth()) {
        return result;
      }
    }
  }

  private int nbTopAdjacentOccupiedSeats(int x, int y) {
    int result = 0;
    for (int i = 1; i <= x; i++) {
      int abs = x - i;
      if(abs >= 0 && ferry.get(abs, y) == SeatState.OCCUPIED) {
        return 1;
      } else if(abs >= 0 && ferry.get(abs, y) == SeatState.EMPTY) {
        return result;
      }
    }
    return result;
  }

  private int nbBottomAdjacentOccupiedSeats(int x, int y) {
    int result = 0;
    for (int i = 1; i < ferry.getHeight() - x; i++) {
      int abs = x + i;
      if(abs < ferry.getHeight() && ferry.get(abs, y) == SeatState.OCCUPIED) {
        return 1;
      }else if(abs < ferry.getHeight() && ferry.get(abs, y) == SeatState.EMPTY) {
        return result;
      }
    }
    return result;
  }

  private int nbLeftAdjacentOccupiedSeats(int x, int y) {
    int result = 0;
    for (int i = 1; i <= y; i++) {
      int ord = y - i;
      if(ord >= 0 && ferry.get(x,ord) == SeatState.OCCUPIED) {
        return 1;
      } else if(ord >= 0 && ferry.get(x,ord) == SeatState.EMPTY) {
        return result;
      }
    }
    return result;
  }

  private int nbRightAdjacentOccupiedSeats(int x, int y) {
    int result = 0;
    for (int i = 1; i < ferry.getWidth() - y; i++) {
      int ord = y + i;
      if(ord < ferry.getWidth() && ferry.get(x,ord) == SeatState.OCCUPIED) {
        return 1;
      } else if(ord < ferry.getWidth() && ferry.get(x,ord) == SeatState.EMPTY) {
        return result;
      }
    }
    return result;
  }
}
