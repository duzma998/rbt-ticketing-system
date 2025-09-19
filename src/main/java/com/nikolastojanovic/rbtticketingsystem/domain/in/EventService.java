package com.nikolastojanovic.rbtticketingsystem.domain.in;

import com.nikolastojanovic.rbtticketingsystem.domain.model.Event;
import com.nikolastojanovic.rbtticketingsystem.domain.model.common.Page;
import com.nikolastojanovic.rbtticketingsystem.domain.model.common.PageResult;
import com.nikolastojanovic.rbtticketingsystem.domain.model.request.EventRequest;
import lombok.NonNull;

public interface EventService {

    PageResult<Event> getEvents(@NonNull Page page);

    Event getEvent(@NonNull Long eventId);

    Event createEvent(@NonNull EventRequest event);

    Event updateEvent(@NonNull Long eventId, @NonNull EventRequest event);

    void deleteEvent(@NonNull Long eventId);
}
