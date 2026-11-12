package org.example.helpdeskspringboot.controller;

import org.example.helpdeskspringboot.entity.Ticket;
import org.example.helpdeskspringboot.entity.User;
import org.example.helpdeskspringboot.repository.UserRepository;
import org.example.helpdeskspringboot.service.CommentService;
import org.example.helpdeskspringboot.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Objects;

@Controller
public class CommentController {

    private final CommentService commentService;
    private final TicketService ticketService;
    private final UserRepository userRepository;

    public CommentController(CommentService commentService,
                             TicketService ticketService,
                             UserRepository userRepository) {
        this.commentService = commentService;
        this.ticketService = ticketService;
        this.userRepository = userRepository;
    }
    @PostMapping("/tickets/{id}/comments")
    public String addComment(@PathVariable("id") Long ticketId,
                             @RequestParam("content") String content,
                             Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));

        Ticket ticket = ticketService.getTicketById(ticketId);

        boolean isAgent = user.getRole() == User.Role.AGENT;
        boolean isOwner = user.getRole() == User.Role.USER
                && ticket.getCreatedBy() != null
                && Objects.equals(ticket.getCreatedBy().getId(), user.getId());

        if (!isAgent && !isOwner) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        commentService.addComment(content, ticketId, user.getId());
        return "redirect:/tickets/" + ticketId;
    }
}
