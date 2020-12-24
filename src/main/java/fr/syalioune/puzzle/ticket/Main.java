package fr.syalioune.puzzle.ticket;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class Main {

  private static final String YOUR_TICKET = "your ticket:";

  private static final String NEARBY_TICKETS = "nearby tickets:";

  public static void main(String[] args) {
    try {
      List<String> lines = Files.readAllLines(Path.of("src/main/resources/ticket-translator-input.txt"));
      if(lines != null) {
        Map<String, String> valueRange = new HashMap<>();
        List<String> nearbyTickets = new LinkedList<>();
        String myTicket = parseInput(lines, valueRange, nearbyTickets);
        TicketTranslator ticketTranslator = new TicketTranslator(valueRange);
        int errorRate = nearbyTickets.stream().flatMap(ticket -> Arrays.stream(ticket.split(",")))
            .filter(value -> !ticketTranslator.isValid(Integer.valueOf(value)))
            .mapToInt(Integer::valueOf).sum();
        System.out.println(errorRate);
        List<List<Integer>> tickets = nearbyTickets.stream().map(line -> line.split(",")).map(arr -> Arrays
            .stream(arr).map(s -> Integer.valueOf(s)).collect(Collectors.toList())).collect(Collectors.toList());
        Map<String, Set<Integer>> possibleCandidates = ticketTranslator.fieldCandidates(tickets);
        Map<String, Integer> definitiveCandidates = ticketTranslator.definitiveCandidates(possibleCandidates);
        AtomicLong result = new AtomicLong(1L);
        List<Integer> myTicketList = Arrays.stream(myTicket.split(",")).map(s -> Integer.valueOf(s)).collect(
            Collectors.toList());
        definitiveCandidates.forEach((field, index) -> {
          if(field.startsWith("departure")) {
            result.updateAndGet(v -> v * myTicketList.get(index - 1));
          }
        });
        System.out.println(result.get());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static String parseInput(List<String> lines, Map<String, String> valueRange,
      List<String> nearbyTickets) {
    String myTicket = "";
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
    return myTicket;
  }
}
