package org.example.helpdeskspringboot.repository;

import org.example.helpdeskspringboot.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByCreatedById(Long userId);
}
