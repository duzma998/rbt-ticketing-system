package com.nikolastojanovic.rbtticketingsystem.domain.service;

import com.nikolastojanovic.rbtticketingsystem.domain.in.EventService;
import com.nikolastojanovic.rbtticketingsystem.domain.model.Event;
import com.nikolastojanovic.rbtticketingsystem.domain.model.common.Page;
import com.nikolastojanovic.rbtticketingsystem.domain.model.common.PageResult;
import com.nikolastojanovic.rbtticketingsystem.domain.out.repository.EventRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class DomainEventService implements EventService {

    private final EventRepository eventRepository;


    @Override
    public PageResult<Event> getEvents(@NonNull Page page) {

        return eventRepository.getEvents(page);
    }
}
