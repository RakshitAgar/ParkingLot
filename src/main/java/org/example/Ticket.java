package org.example;

import java.util.UUID;

public class Ticket {
    private final String ticketNumber;

    public Ticket() {
        this.ticketNumber = generateTicketNumber();
    }

    private String generateTicketNumber() {
        return UUID.randomUUID().toString();
    }

}
