package com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.implementation;

import com.nikolastojanovic.rbtticketingsystem.domain.exception.CustomException;
import com.nikolastojanovic.rbtticketingsystem.domain.exception.Error;
import com.nikolastojanovic.rbtticketingsystem.domain.model.Event;
import com.nikolastojanovic.rbtticketingsystem.domain.model.common.Page;
import com.nikolastojanovic.rbtticketingsystem.domain.model.common.PageResult;
import com.nikolastojanovic.rbtticketingsystem.domain.out.repository.EventRepository;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.jpa.EventRepositoryJpa;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.jpa.UserRepositoryJpa;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.mapper.EventRepositoryMapper;

import static com.nikolastojanovic.rbtticketingsystem.infrastructure.util.PageUtil.toPageResult;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class EventRepositoryImpl implements EventRepository {

    private final EventRepositoryJpa eventRepositoryJpa;
    private final UserRepositoryJpa userRepositoryJpa;
    private final EventRepositoryMapper eventMapper;

    @Override
    public PageResult<Event> getEvents(@NonNull Page page) {
        Pageable pageable = PageRequest.of(page.pageNumber(), page.size());
        var entities = eventRepositoryJpa.findAll(pageable);

        return toPageResult(entities, eventMapper::toDomain);
    }

    @Override
    public Event getEvent(@NonNull Long eventId) {

        return eventRepositoryJpa.findById(eventId).map(eventMapper::toDomain).orElse(null);
    }

    @Override
    public Event saveEvent(@NonNull Event event) {
        var creatorEntity = userRepositoryJpa.findById(event.creatorId()).orElseThrow(() -> new CustomException(Error.NOT_FOUND, "Creator not found."));
        var eventEntity = eventMapper.toEntity(event, creatorEntity);
        var savedEntity = eventRepositoryJpa.save(eventEntity);
        return eventMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteEvent(@NonNull Long eventId) {
        eventRepositoryJpa.deleteById(eventId);
    }

    @Override
    public Event updateEvent(@NonNull Event event) {

        var creatorEntity = userRepositoryJpa.findById(event.creatorId()).orElseThrow(() -> new CustomException(Error.NOT_FOUND, "Creator not found."));

        var eventEntity = eventMapper.toEntity(event, creatorEntity);
        var updatedEntity = eventRepositoryJpa.save(eventEntity);
        return eventMapper.toDomain(updatedEntity);
    }

    @Override
    public void updateAvailableTickets(@NonNull Long eventId, @NonNull Integer newAvailableTickets) {
        eventRepositoryJpa.updateAvailableTickets(eventId, newAvailableTickets);
    }


}
