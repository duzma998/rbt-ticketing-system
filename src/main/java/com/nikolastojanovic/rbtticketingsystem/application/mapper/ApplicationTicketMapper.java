package com.nikolastojanovic.rbtticketingsystem.application.mapper;

import com.nikolastojanovic.rbtticketingsystem.application.model.response.TicketValidationResponse;
import org.springframework.stereotype.Component;

@Component
public class ApplicationTicketMapper {
    public TicketValidationResponse toResponse(boolean isValid) {
        return new TicketValidationResponse(isValid);
    }
}
