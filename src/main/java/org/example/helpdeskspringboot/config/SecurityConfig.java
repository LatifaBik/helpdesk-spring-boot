package org.example.helpdeskspringboot.config;

import org.example.helpdeskspringboot.repository.UserRepository;
import org.example.helpdeskspringboot.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/login",
                                "/error",
                                "/css/**",
                                "/js/**",
                                "/images/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login.defaultSuccessUrl("/tickets"))
                .logout(Customizer.withDefaults());

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    @Profile("local")
    public CommandLineRunner createTestUser(
            UserRepository userRepository, UserService userService) {

        return args -> {
            //String username = "testuser";
            String username = "helpdesk-test-user";
            String password = System.getenv("HELPDESK_TEST_PASSWORD");

            if (password != null && !password.isBlank()
                    && userRepository.findByUsername(username).isEmpty()) {
                userService.createUser(username, password);
            }
        };
    }
}
