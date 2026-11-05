package org.example.helpdeskspringboot.service;

import org.example.helpdeskspringboot.entity.Comment;
import org.example.helpdeskspringboot.repository.CommentRepository;
import org.example.helpdeskspringboot.repository.TicketRepository;
import org.example.helpdeskspringboot.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository,
                          TicketRepository ticketRepository,
                          UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    public void addComment(String content, Long ticketId, Long userId) {
        Comment comment = new Comment();
        comment.setContent(content);

        comment.setTicket(ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Ärendet finns inte")));

        comment.setAuthor(userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Användaren finns inte")));

        commentRepository.save(comment);
    }

    public List<Comment> getCommentsByTicketId(Long ticketId) {
        return commentRepository.findByTicketIdOrderByIdAsc(ticketId);
    }
}
