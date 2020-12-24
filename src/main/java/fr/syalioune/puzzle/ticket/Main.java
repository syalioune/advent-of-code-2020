package fr.syalioune.puzzle.ticket;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {

  private static final String YOUR_TICKET = "your ticket:";

  private static final String NEARBY_TICKETS = "nearby tickets:";

  public static void main(String[] args) {
    try {
      List<String> lines = Files.readAllLines(Path.of("src/main/resources/ticket-translator-input.txt"));
      if(lines != null) {
        Map<String, String> valueRange = new HashMap<>();
        String myTicket = "";
        List<String> nearbyTickets = new LinkedList<>();
        int part = 1;
        for (int i = 0; i < lines.size(); i++) {
          String line = lines.get(i);
          if(line.isBlank()) {
            part++;
            continue;
          }
          if(part == 1) {
            String[] parts = line.split(":");
            valueRange.put(parts[0], parts[1].strip());
          } else if(part == 2 && !YOUR_TICKET.equals(line)) {
            myTicket = line;
          } else if(part == 3 && !NEARBY_TICKETS.equals(line)) {
            nearbyTickets.add(line);
          }
        }
        TicketTranslator ticketTranslator = new TicketTranslator(valueRange);
        int errorRate = nearbyTickets.stream().flatMap(ticket -> Arrays.stream(ticket.split(",")))
            .filter(value -> !ticketTranslator.isValid(Integer.valueOf(value)))
            .mapToInt(Integer::valueOf).sum();
        System.out.println(errorRate);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
