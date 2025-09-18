package com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.jpa;

import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepositoryJpa extends JpaRepository<EventEntity, Long> {
}
