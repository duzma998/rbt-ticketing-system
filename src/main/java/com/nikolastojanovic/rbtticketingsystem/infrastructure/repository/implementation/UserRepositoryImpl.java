package com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.implementation;

import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.jpa.UserRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl {
    private final UserRepositoryJpa userRepositoryJpa;
}
