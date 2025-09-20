package com.nikolastojanovic.rbtticketingsystem.application.controller.rest;

import com.nikolastojanovic.rbtticketingsystem.application.model.request.ApplicationEventRequest;
import com.nikolastojanovic.rbtticketingsystem.application.model.response.EventResponse;
import com.nikolastojanovic.rbtticketingsystem.application.service.ApplicationEventService;
import com.nikolastojanovic.rbtticketingsystem.domain.model.common.PageResult;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.Min;

import java.security.Principal;

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

        var events = applicationEventService.getEvents(page, size);

        return ResponseEntity.ok(events);
    }

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@RequestBody @NonNull ApplicationEventRequest eventRequest, @AuthenticationPrincipal Principal principal) {
        var event = applicationEventService.createEvent(eventRequest, principal);
        return ResponseEntity.ok(event);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable @NonNull Long id) {

        var event = applicationEventService.getEventById(id);

        return ResponseEntity.ok(event);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EventResponse> deleteEvent(@PathVariable @NonNull Long id) {
        applicationEventService.deleteEvent(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EventResponse> updateEvent(@RequestBody @NonNull ApplicationEventRequest eventRequest, @PathVariable @NonNull Long id, @AuthenticationPrincipal Principal principal) {
        var event = applicationEventService.updateEvent( id, eventRequest, principal);

        return ResponseEntity.ok(event);
    }
}
