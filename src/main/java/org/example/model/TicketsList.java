package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TicketsList {
    @JsonProperty("tickets")
    private List<Ticket> tickets;
}
