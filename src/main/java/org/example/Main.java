package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Ticket;
import org.example.model.TicketsList;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TicketsList ticketsList = mapper.readValue(new File("tickets.json"), TicketsList.class);

        Map<String, Duration> minDuration = new HashMap<>();

        List<Ticket> vvoToTlv = ticketsList.getTickets().stream()
                .filter(ticket -> "VVO".equals(ticket.getOrigin()) && "TLV".equals(ticket.getDestination()))
                .peek(ticket -> {
                    Duration current = ticket.getFlightDuration();
                    Duration min = minDuration.get(ticket.getCarrier());
                    if (min == null || current.compareTo(min) < 0) {
                        minDuration.put(ticket.getCarrier(), current);
                    }
                })
                .toList();

        minDuration.forEach((carrier, duration) ->
                System.out.printf("%s: %dч. %dм.%n",
                        carrier, duration.toHours(), duration.toMinutesPart())
        );


        System.out.println(getPriceDifference(vvoToTlv));
    }

    private static double getPriceDifference(List<Ticket> tickets) {
        int[] prices = tickets.stream()
                .mapToInt(Ticket::getPrice)
                .sorted()
                .toArray();

        double average = Arrays.stream(prices).average().orElse(0);
        double median = prices.length % 2 == 0 ?
                (prices[prices.length / 2 - 1] + prices[prices.length / 2]) / 2.0 :
                prices[prices.length / 2];

        return average - median;
    }
}