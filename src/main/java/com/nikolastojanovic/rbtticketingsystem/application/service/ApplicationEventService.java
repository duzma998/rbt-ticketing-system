package com.nikolastojanovic.rbtticketingsystem.application.service;


import com.nikolastojanovic.rbtticketingsystem.application.mapper.ApplicationEventMapper;
import com.nikolastojanovic.rbtticketingsystem.application.model.request.ApplicationEventRequest;
import com.nikolastojanovic.rbtticketingsystem.application.model.response.EventResponse;
import com.nikolastojanovic.rbtticketingsystem.domain.in.EventService;
import com.nikolastojanovic.rbtticketingsystem.domain.model.common.Page;
import com.nikolastojanovic.rbtticketingsystem.domain.model.common.PageResult;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.nikolastojanovic.rbtticketingsystem.infrastructure.util.PageUtil.toPageResult;

@RequiredArgsConstructor
@Service
public class ApplicationEventService {

    private final EventService eventService;
    private final ApplicationEventMapper eventMapper;

    @Transactional(readOnly = true)
    public PageResult<EventResponse> getEvents(int page, int size) {

        var events = eventService.getEvents(new Page(page, size));
        return toPageResult(events, eventMapper::toResponse);
    }

    public EventResponse getEventById(@NonNull Long id) {

        var event = eventService.getEvent(id);

        return eventMapper.toResponse(event);
    }

    @Transactional
    public EventResponse createEvent(@NonNull ApplicationEventRequest eventRequest, String principal) {
        var event = eventMapper.toRequest(eventRequest, principal);
        var savedEvent = eventService.createEvent(event);
        return eventMapper.toResponse(savedEvent);
    }

    @Transactional
    public EventResponse updateEvent(@NonNull Long id, @NonNull ApplicationEventRequest eventRequest, String principal) {
        var event = eventMapper.toRequest(eventRequest, principal);
        var updatedEvent = eventService.updateEvent(id, event);
        return eventMapper.toResponse(updatedEvent);
    }

    @Transactional
    public void cancelEvent(@NonNull Long id) {
        eventService.cancelEvent(id);
    }
}
