package com.nikolastojanovic.rbtticketingsystem.infrastructure.util;

import com.nikolastojanovic.rbtticketingsystem.domain.model.common.PageResult;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;

import java.util.function.Function;

@UtilityClass
public class PageUtil {

    public static <T, R> PageResult<R> toPageResult(Page<T> page, Function<T, R> mapper) {

        return PageResult.<R>builder()
                .content(page.getContent().stream().map(mapper).toList())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .currentPage(page.getNumber())
                .size(page.getSize())
                .build();

    }

    public static <T, R> PageResult<R> toPageResult(PageResult<T> page, Function<T, R> mapper) {

        return PageResult.<R>builder()
                .content(page.content().stream().map(mapper).toList())
                .totalElements(page.totalElements())
                .totalPages(page.totalPages())
                .currentPage(page.currentPage())
                .size(page.size())
                .build();

    }
}
