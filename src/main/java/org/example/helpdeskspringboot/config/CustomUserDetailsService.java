package org.example.helpdeskspringboot.config;

import org.example.helpdeskspringboot.entity.User;
import org.example.helpdeskspringboot.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .filter(account -> account.getPassword() != null
                        && !account.getPassword().isBlank()
                        && account.getRole() != null)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Inget inloggningsbart konto"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
