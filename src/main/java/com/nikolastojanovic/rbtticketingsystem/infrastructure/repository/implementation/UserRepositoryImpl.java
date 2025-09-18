package com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.implementation;

import com.nikolastojanovic.rbtticketingsystem.domain.model.User;
import com.nikolastojanovic.rbtticketingsystem.domain.model.common.Page;
import com.nikolastojanovic.rbtticketingsystem.domain.model.common.PageResult;
import com.nikolastojanovic.rbtticketingsystem.domain.out.repository.UserRepository;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.jpa.UserRepositoryJpa;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.mapper.UserRepositoryMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserRepositoryJpa userRepositoryJpa;
    private final UserRepositoryMapper userMapper;

    @Override
    public Optional<User> getById(@NonNull Long id) {
        return userRepositoryJpa.findById(id).map(userMapper::toDomain);
    }

    @Override
    public Optional<User> getByUsername(@NonNull String username) {
        return userRepositoryJpa.findByUsername(username).map(userMapper::toDomain);
    }

    @Override
    public User save(@NonNull User user) {
        return userRepositoryJpa.save(userMapper::toEntity)
    }
}
