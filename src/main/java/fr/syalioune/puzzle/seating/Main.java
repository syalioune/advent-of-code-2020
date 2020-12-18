package fr.syalioune.puzzle.seating;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    try {
      List<String> lines = Files.readAllLines(Path.of("src/main/resources/seating-system-input.txt"));
      if(lines != null) {
        Ferry ferry = initializeFerry(lines);
        NeighbourHoodCalculator neighbourHoodCalculator = new NeighbourHoodCalculator(ferry);
        SeatUpdater seatUpdater = new SeatUpdater();
        boolean run =  true;
        int nbOfOccupiedSeats = 0;
        while(run) {
          run = false;
          nbOfOccupiedSeats = 0;
          SeatState[][] _seats = new SeatState[lines.size()][lines.get(0).length()];
          for (int i = 0; i < ferry.getHeight(); i++) {
            for (int j = 0; j < ferry.getWidth(); j++) {
              SeatState currentState = ferry.get(i,j);
              SeatState nextState = seatUpdater.nextState(currentState, neighbourHoodCalculator.nbOfAdjacentOccupiedSeats(i,j));
              run = run || (currentState != nextState);
              if(nextState == SeatState.OCCUPIED) {
                nbOfOccupiedSeats++;
              }
              _seats[i][j] = nextState;
            }
          }
          ferry = new Ferry(_seats);
          neighbourHoodCalculator = new NeighbourHoodCalculator(ferry);
        }
        System.out.println("Number of occupied seats : "+nbOfOccupiedSeats);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static Ferry initializeFerry(List<String> lines) {
    SeatState[][] seats = new SeatState[lines.size()][lines.get(0).length()];
    for (int i = 0; i < lines.size(); i++) {
      String line = lines.get(i);
      for (int j = 0; j < line.length() ; j++) {
        String seat = line.substring(j,j+1);
        seats[i][j] = SeatState.fromCharacter(seat);
      }
    }
    Ferry ferry = new Ferry(seats);
    return ferry;
  }
}
