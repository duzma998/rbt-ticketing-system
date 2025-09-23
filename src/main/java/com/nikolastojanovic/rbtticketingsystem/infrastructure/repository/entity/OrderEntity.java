package com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.entity;

import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.enums.InfraOrderMethod;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.enums.InfraOrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "User is required")
    UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    @NotNull(message = "Event is required")
    EventEntity event;

    @Column(name = "ticket_count", nullable = false)
    @NotNull(message = "Ticket count is required")
    @Positive(message = "Ticket count must be positive")
    Integer ticketCount;

    @Column(name = "total_amount", nullable = false)
    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Total amount must be zero or positive")
    Double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    @Builder.Default
    InfraOrderStatus status = InfraOrderStatus.ACTIVE;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_method", nullable = false, length = 50)
    @Builder.Default
    InfraOrderMethod orderMethod = InfraOrderMethod.API;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    ZonedDateTime createdAt = ZonedDateTime.now();

    @Column(name = "updated_at")
    ZonedDateTime updatedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<TicketEntity> tickets;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = ZonedDateTime.now();
    }

}
