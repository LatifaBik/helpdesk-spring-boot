package org.example.helpdeskspringboot.repository;

import org.example.helpdeskspringboot.entity.Comment;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface CommentRepository extends ListCrudRepository<Comment, Long> {

    List<Comment> findByTicketIdOrderByIdAsc(Long ticketId);
}
