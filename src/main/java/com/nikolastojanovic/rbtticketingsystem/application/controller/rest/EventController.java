package com.nikolastojanovic.rbtticketingsystem.application.controller.rest;

import com.nikolastojanovic.rbtticketingsystem.application.model.response.EventResponse;
import com.nikolastojanovic.rbtticketingsystem.application.service.ApplicationEventService;
import com.nikolastojanovic.rbtticketingsystem.domain.model.common.PageResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
@Slf4j
@Validated
public class EventController {

    private final ApplicationEventService applicationEventService;

    @GetMapping
    public ResponseEntity<PageResult<EventResponse>> getPublicEvents(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) int size) {
        
        log.info("Fetching public events - page: {}, size: {}", page, size);

        var events = applicationEventService.getEvents(page, size);

        return ResponseEntity.ok(events);
    }
}
