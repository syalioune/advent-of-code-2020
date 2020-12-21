package fr.syalioune.puzzle.shuttle;

public class BusDeparture {

  private Integer busId;

  private Integer timestamp;

  public int getBusId() {
    return busId;
  }

  public int getTimestamp() {
    return timestamp;
  }

  public void setBusId(Integer busId) {
    this.busId = busId;
  }

  public void setTimestamp(Integer timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public String toString() {
    return "BusDeparture{" +
        "busId=" + busId +
        ", timestamp=" + timestamp +
        '}';
  }
}
