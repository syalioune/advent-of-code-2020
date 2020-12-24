package fr.syalioune.puzzle.recitation;

import java.util.Arrays;

public class Main {

  public static void main(String[] args) {
    Repeater repeater = new Repeater(Arrays.asList(16,12,1,0,15,7,11));
    System.out.println(repeater.spokenNumber(2020));
    System.out.println(repeater.spokenNumber(30000000));
  }
}


