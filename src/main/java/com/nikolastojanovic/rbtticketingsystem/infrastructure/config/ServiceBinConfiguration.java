package com.nikolastojanovic.rbtticketingsystem.infrastructure.config;

import com.nikolastojanovic.rbtticketingsystem.domain.in.EventService;
import com.nikolastojanovic.rbtticketingsystem.domain.in.UserService;
import com.nikolastojanovic.rbtticketingsystem.domain.out.repository.EventRepository;
import com.nikolastojanovic.rbtticketingsystem.domain.out.repository.UserRepository;
import com.nikolastojanovic.rbtticketingsystem.domain.out.service.AuthenticationService;
import com.nikolastojanovic.rbtticketingsystem.domain.service.DomainEventService;
import com.nikolastojanovic.rbtticketingsystem.domain.service.DomainUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceBinConfiguration {

    @Bean
    public UserService userService(UserRepository userRepository, AuthenticationService authenticationService) {
        return new DomainUserService(userRepository, authenticationService);
    }

    @Bean
    public EventService eventService(EventRepository eventRepository) {
        return new DomainEventService(eventRepository);
    }
}
