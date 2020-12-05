package fr.syalioune.puzzle.reportrepair;

import java.util.Objects;

public class Tuple {

  private int first;

  private int second;

  public static final Tuple EMPTY = new Tuple(0,0);

  public Tuple(int x, int y) {
    first = x;
    second = y;
  }

  public int sum() {
    return first + second;
  }

  public int times() {
    return first * second;
  }

  public int getFirst() {
    return first;
  }

  public int getSecond() {
    return second;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Tuple tuple = (Tuple) o;
    return first == tuple.first &&
        second == tuple.second || first == tuple.second &&
        second == tuple.first;
  }

  @Override
  public int hashCode() {
    return Objects.hash(first, second) + Objects.hash(second, first);
  }

  @Override
  public String toString() {
    return "Tuple{" +
        "first=" + first +
        ", second=" + second +
        ", sum=" + sum() +
        ", times=" + times() +
        '}';
  }
}
