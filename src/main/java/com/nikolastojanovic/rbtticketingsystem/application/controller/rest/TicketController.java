package com.nikolastojanovic.rbtticketingsystem.application.controller.rest;

import com.nikolastojanovic.rbtticketingsystem.application.mapper.ApplicationTicketMapper;
import com.nikolastojanovic.rbtticketingsystem.application.model.response.TicketValidationResponse;
import com.nikolastojanovic.rbtticketingsystem.application.service.ApplicationTicketService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private final ApplicationTicketMapper ticketMapper;
    private final ApplicationTicketService ticketService;

    @PostMapping("/{ticketCode}/validate")
    public ResponseEntity<TicketValidationResponse> validateTicket(@NonNull @PathVariable String ticketCode) {
        return  ResponseEntity.ok(ticketMapper.toResponse(ticketService.isTicketValid(ticketCode)));
    }

    @PostMapping("/{ticketCode}/cancel")
    public ResponseEntity<Void> cancelTicket(@NonNull @PathVariable String ticketCode) {
        ticketService.cancelTicket(ticketCode);
        return  ResponseEntity.noContent().build();
    }
}
