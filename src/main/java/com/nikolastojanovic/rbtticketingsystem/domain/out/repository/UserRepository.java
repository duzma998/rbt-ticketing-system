package com.nikolastojanovic.rbtticketingsystem.domain.out.repository;

import java.util.Optional;
import lombok.NonNull;
import com.nikolastojanovic.rbtticketingsystem.domain.exception.TicketingException;
import com.nikolastojanovic.rbtticketingsystem.domain.exception.Error;
import com.nikolastojanovic.rbtticketingsystem.domain.model.User;

public interface UserRepository {

  Optional<User> getByUsername(@NonNull String username);

  default User getByUsernameOrThrow(@NonNull String username) {
    return getByUsername(username).orElseThrow(
        () -> new TicketingException(Error.NOT_FOUND, "Username not found"));
  }

  User save(@NonNull User user);

}
