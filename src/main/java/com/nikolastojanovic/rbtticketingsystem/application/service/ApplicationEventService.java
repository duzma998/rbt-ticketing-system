package com.nikolastojanovic.rbtticketingsystem.application.service;


import com.nikolastojanovic.rbtticketingsystem.application.mapper.ApplicationEventMapper;
import com.nikolastojanovic.rbtticketingsystem.application.model.response.EventResponse;
import com.nikolastojanovic.rbtticketingsystem.domain.in.EventService;
import com.nikolastojanovic.rbtticketingsystem.domain.model.common.Page;
import com.nikolastojanovic.rbtticketingsystem.domain.model.common.PageResult;
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
}
