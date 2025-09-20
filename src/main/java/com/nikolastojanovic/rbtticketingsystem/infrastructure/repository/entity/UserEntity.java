package com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.entity;

import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.UserRole;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.enums.InfraUserRole;
import lombok.*;
import lombok.experimental.FieldDefaults;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "username", unique = true, nullable = false, length = 50)
    @NotBlank(message = "Username is required")
    @Size(max = 50, message = "Username must not exceed 50 characters")
    String username;

    @Column(name = "email", unique = true, nullable = false, length = 100)
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    String email;

    @Column(name = "password_encrypted", nullable = false)
    @NotBlank(message = "Password is required")
    String passwordEncrypted;

    @Column(name = "first_name", length = 100)
    @Size(max = 100, message = "First name must not exceed 100 characters")
    String firstName;

    @Column(name = "last_name", length = 100)
    @Size(max = 100, message = "Last name must not exceed 100 characters")
    String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    @Builder.Default
    InfraUserRole role = InfraUserRole.USER;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<EventEntity> createdEvents;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<OrderEntity> orders;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<TicketEntity> tickets;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
