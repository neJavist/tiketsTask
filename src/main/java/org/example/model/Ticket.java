package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class Ticket {
    @JsonProperty("origin")
    private String origin;

    @JsonProperty("origin_name")
    private String originName;

    @JsonProperty("destination")
    private String destination;

    @JsonProperty("destination_name")
    private String destinationName;

    @JsonProperty("departure_date")
    private String departureDate;

    @JsonProperty("departure_time")
    private String departureTime;

    @JsonProperty("arrival_date")
    private String arrivalDate;

    @JsonProperty("arrival_time")
    private String arrivalTime;

    @JsonProperty("carrier")
    private String carrier;

    @JsonProperty("stops")
    private int stops;

    @JsonProperty("price")
    private int price;

    public LocalDateTime getDepartureDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy H:mm");
        String dateTimeStr = departureDate + " " + departureTime;
        return LocalDateTime.parse(dateTimeStr, formatter);
    }

    public LocalDateTime getArrivalDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy H:mm");
        String dateTimeStr = arrivalDate + " " + arrivalTime;
        return LocalDateTime.parse(dateTimeStr, formatter);
    }

    public Duration getFlightDuration() {
        return Duration.between(getDepartureDateTime(), getArrivalDateTime());
    }
}
