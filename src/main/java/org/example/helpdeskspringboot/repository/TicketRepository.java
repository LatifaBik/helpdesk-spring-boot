package org.example.helpdeskspringboot.repository;

import org.example.helpdeskspringboot.entity.Ticket;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;


public interface TicketRepository extends ListCrudRepository<Ticket, Long> {

    List<Ticket> findByCreatedById(Long userId);
}
