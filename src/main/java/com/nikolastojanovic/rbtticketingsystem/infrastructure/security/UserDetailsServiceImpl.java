package com.nikolastojanovic.rbtticketingsystem.infrastructure.security;


import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.UserRole;
import com.nikolastojanovic.rbtticketingsystem.domain.out.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user = userRepository.getByUsernameOrThrow(username);
    var authorities = new ArrayList<SimpleGrantedAuthority>();
    if (user.role().equals(UserRole.ADMIN)) {
      authorities.addAll(List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
          new SimpleGrantedAuthority("ROLE_USER")));
    } else if (user.role().equals(UserRole.USER)) {
      authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    }
    return org.springframework.security.core.userdetails.User.builder()
        .username(user.username())
        .password(user.password())
        .authorities(authorities)
        .build();
  }
}
