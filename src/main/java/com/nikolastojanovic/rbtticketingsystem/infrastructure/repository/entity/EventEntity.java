package com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.entity;

import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.enums.InfraEventStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "events")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Event name is required")
    @Size(max = 255, message = "Event name must not exceed 255 characters")
    String name;

    @Column(name = "description", columnDefinition = "TEXT")
    String description;

    @Column(name = "event_type", nullable = false, length = 100)
    @NotBlank(message = "Event type is required")
    @Size(max = 100, message = "Event type must not exceed 100 characters")
    String eventType;

    @Column(name = "venue_name")
    @Size(max = 255, message = "Venue name must not exceed 255 characters")
    String venueName;

    @Column(name = "venue_address", columnDefinition = "TEXT")
    String venueAddress;

    @Column(name = "event_date", nullable = false)
    @NotNull(message = "Event date is required")
    ZonedDateTime eventDate;

    @Column(name = "total_tickets", nullable = false)
    @NotNull(message = "Total tickets is required")
    @Positive(message = "Total tickets must be positive")
    Integer totalTickets;

    @Column(name = "available_tickets", nullable = false)
    @NotNull(message = "Available tickets is required")
    @PositiveOrZero(message = "Available tickets must be zero or positive")
    Integer availableTickets;

    @Column(name = "max_tickets_per_purchase", nullable = false)
    @NotNull(message = "Max tickets per purchase is required")
    @Positive(message = "Max tickets per purchase must be positive")
    @Builder.Default
    Integer maxTicketsPerPurchase = 10;

    @Column(name = "ticket_price", nullable = false)
    @NotNull(message = "Ticket price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Ticket price must be zero or positive")
    Double ticketPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    @Builder.Default
    InfraEventStatus status = InfraEventStatus.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    @NotNull(message = "Created by is required")
    UserEntity createdBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    ZonedDateTime createdAt = ZonedDateTime.now();

    @Column(name = "updated_at")
    ZonedDateTime updatedAt;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<OrderEntity
            > orders;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<TicketEntity> tickets;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = ZonedDateTime.now();
    }

    public boolean hasAvailableTickets(Integer requestedTickets) {
        return availableTickets >= requestedTickets;
    }

    public boolean isWithinPurchaseLimit(Integer requestedTickets) {
        return requestedTickets <= maxTicketsPerPurchase;
    }

    public void reserveTickets(Integer ticketCount) {
        if (!hasAvailableTickets(ticketCount)) {
            throw new IllegalStateException("Not enough available tickets");
        }
        availableTickets -= ticketCount;
    }

    public void releaseTickets(Integer ticketCount) {
        availableTickets = Math.min(totalTickets, availableTickets + ticketCount);
    }
}
