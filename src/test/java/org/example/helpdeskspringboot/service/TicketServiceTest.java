package org.example.helpdeskspringboot.service;

import org.example.helpdeskspringboot.entity.TicketStatus;
import org.example.helpdeskspringboot.entity.User;
import org.example.helpdeskspringboot.repository.TicketRepository;
import org.example.helpdeskspringboot.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TicketServiceTest {
    @Test
    void userCannotChangeTicketStatus() {
        UserRepository userRepository = mock(UserRepository.class);
        TicketRepository ticketRepository = mock(TicketRepository.class);
        TicketService ticketService = new TicketService(ticketRepository, userRepository);

        User user = new User();
        user.setRole(User.Role.USER);
        when(userRepository.findByUsername("helpdesk-test-user"))
                .thenReturn(Optional.of(user));

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> ticketService.updateTicketStatus(
                        3L, TicketStatus.IN_PROGRESS, "helpdesk-test-user")
        );

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
        verifyNoInteractions(ticketRepository);
    }

}
