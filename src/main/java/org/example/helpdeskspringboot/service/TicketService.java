package org.example.helpdeskspringboot.service;

import org.example.helpdeskspringboot.entity.Ticket;
import org.example.helpdeskspringboot.entity.User;
import org.example.helpdeskspringboot.repository.TicketRepository;
import org.example.helpdeskspringboot.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketService(TicketRepository ticketRepository,
                         UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    public Ticket createTicket(String title,
                               String description,
                               Long userId) {

        User creator = userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Användaren finns inte"));

        Ticket ticket = new Ticket();
        ticket.setTitle(title);
        ticket.setDescription(description);
        ticket.setCreatedBy(creator);

        return ticketRepository.save(ticket);
    }

    public List<Ticket> getTicketsByUserId(Long userId) {
        return ticketRepository.findByCreatedById(userId);
    }
}
