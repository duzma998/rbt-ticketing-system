package com.nikolastojanovic.rbtticketingsystem.domain.out.repository;

import com.nikolastojanovic.rbtticketingsystem.domain.model.Event;
import com.nikolastojanovic.rbtticketingsystem.domain.model.common.Page;
import com.nikolastojanovic.rbtticketingsystem.domain.model.common.PageResult;
import lombok.NonNull;


public interface EventRepository {
    PageResult<Event> getEvents(@NonNull Page page);
    Event getEvent(@NonNull Long eventId);
    Event saveEvent(@NonNull Event event);
    void deleteEvent(@NonNull Long eventId);
    Event updateEvent(@NonNull Event event);
}
