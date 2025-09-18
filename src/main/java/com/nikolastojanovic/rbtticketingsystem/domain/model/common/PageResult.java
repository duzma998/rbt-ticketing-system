package com.nikolastojanovic.rbtticketingsystem.domain.model.common;

import java.util.List;

import lombok.Builder;

@Builder
public record PageResult<T>(
        List<T> content,
        long totalElements,
        int totalPages
) {
}
