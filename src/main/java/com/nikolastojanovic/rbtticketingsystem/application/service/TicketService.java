package com.nikolastojanovic.rbtticketingsystem.application.service;

import org.springframework.stereotype.Service;

@Service
public class TicketService {

    public String createTicket(String user) {
        return "Ticket Created for user " + user;
    }
}
