package com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.mapper;

import com.nikolastojanovic.rbtticketingsystem.domain.model.User;
import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.UserRole;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.entity.UserEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class UserRepositoryMapper {

    public User toDomain(@NotNull UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getUsername(),
                entity.getPasswordEncrypted(),
                entity.getEmail(),
                entity.getFirstName(),
                entity.getLastName(),
                UserRole.valueOf(entity.getRole().name()),
                entity.getUpdatedAt(),
                entity.getCreatedAt()
        );
    }

    public UserEntity toEntity(@NotNull User user) {
        return new UserEntity(
                user.username(),
                user.password(),
                user.email(),
                user.firstName(),
                user.lastName(),
                UserRole.valueOf(user.role()),
                user.updatedAt(),
                user.createdAt()
        );
    }
}
