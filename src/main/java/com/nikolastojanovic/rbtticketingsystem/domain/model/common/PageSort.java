package com.nikolastojanovic.rbtticketingsystem.domain.model.common;

public record PageSort(
        String sortBy,
        String sortDirection,
        Page page
) {
}
