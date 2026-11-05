package org.example.helpdeskspringboot.repository;

import org.example.helpdeskspringboot.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository  extends JpaRepository<Comment, Long>  {

    List<Comment> findByTicketIdOrderByIdAsc(Long ticketId);

}
