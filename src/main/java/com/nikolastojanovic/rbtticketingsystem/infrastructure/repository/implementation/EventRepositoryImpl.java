package com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.implementation;

import com.nikolastojanovic.rbtticketingsystem.domain.model.Event;
import com.nikolastojanovic.rbtticketingsystem.domain.model.common.Page;
import com.nikolastojanovic.rbtticketingsystem.domain.model.common.PageResult;
import com.nikolastojanovic.rbtticketingsystem.domain.out.repository.EventRepository;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.jpa.EventRepositoryJpa;
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
    private final EventRepositoryMapper eventMapper;

    @Override
    public PageResult<Event> getEvents(@NonNull Page page) {
        Pageable pageable = PageRequest.of(page.pageNumber(), page.size());
        var entities = eventRepositoryJpa.findAll(pageable);

        return toPageResult(entities, eventMapper::toDomain);
    }

}
