package com.nikolastojanovic.rbtticketingsystem.application.service;

import com.nikolastojanovic.rbtticketingsystem.domain.in.TicketService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationTicketService {

    private final TicketService ticketService;

    public boolean isTicketValid(String ticketCode) {
        return ticketService.isTicketValid(ticketCode);
    }

    public void cancelTicket(@NonNull String ticketCode) {
        ticketService.cancelTicket(ticketCode);
    }
}
