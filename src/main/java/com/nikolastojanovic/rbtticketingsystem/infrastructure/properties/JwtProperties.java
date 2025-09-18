package com.nikolastojanovic.rbtticketingsystem.infrastructure.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("security.jwt")
public class JwtProperties {

  String secretKey;
  Long expiration;
}
