package com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.jpa;

import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryJpa extends JpaRepository<UserEntity, Long> {
}
