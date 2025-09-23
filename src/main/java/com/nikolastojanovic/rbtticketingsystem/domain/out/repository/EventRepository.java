package com.nikolastojanovic.rbtticketingsystem.domain.out.repository;

import com.nikolastojanovic.rbtticketingsystem.domain.model.Event;
import com.nikolastojanovic.rbtticketingsystem.domain.model.common.Page;
import com.nikolastojanovic.rbtticketingsystem.domain.model.common.PageResult;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.entity.EventEntity;
import lombok.NonNull;

import java.time.ZonedDateTime;
import java.util.List;


public interface EventRepository {

    PageResult<Event> getEvents(@NonNull Page page);

    Event getEvent(@NonNull Long eventId);

    Event saveEvent(@NonNull Event event);

    void deleteEvent(@NonNull Long eventId);

    Event updateEvent(@NonNull Event event);

    void updateAvailableTickets(@NonNull Long eventId, @NonNull Integer newAvailableTickets);
}
