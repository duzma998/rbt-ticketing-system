package com.nikolastojanovic.rbtticketingsystem.application.web.controller;

import com.nikolastojanovic.rbtticketingsystem.application.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @GetMapping("/create")
    public ResponseEntity<String> createTicket(@RequestParam String user) {
        return ResponseEntity.ok(ticketService.createTicket(user));
    }
}
