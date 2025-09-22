package com.nikolastojanovic.rbtticketingsystem.domain.out.repository;

import java.util.Optional;
import lombok.NonNull;
import com.nikolastojanovic.rbtticketingsystem.domain.exception.CustomException;
import com.nikolastojanovic.rbtticketingsystem.domain.exception.Error;
import com.nikolastojanovic.rbtticketingsystem.domain.model.User;

public interface UserRepository {

  Optional<User> getById(@NonNull Long id);

  default User getByIdOrThrow(@NonNull Long id) {
    return getById(id).orElseThrow(
        () -> new CustomException(Error.NOT_FOUND, "User not found"));
  }

  Optional<User> getByUsername(@NonNull String username);

  default User getByUsernameOrThrow(@NonNull String username) {
    return getByUsername(username).orElseThrow(
        () -> new CustomException(Error.NOT_FOUND, "Username not found"));
  }

  User save(@NonNull User user);

}
