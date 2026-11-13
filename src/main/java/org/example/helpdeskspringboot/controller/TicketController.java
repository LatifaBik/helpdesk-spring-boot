package org.example.helpdeskspringboot.controller;


import org.example.helpdeskspringboot.entity.Ticket;
import org.example.helpdeskspringboot.entity.TicketStatus;
import org.example.helpdeskspringboot.entity.User;
import org.example.helpdeskspringboot.repository.UserRepository;
import org.example.helpdeskspringboot.service.CommentService;
import org.example.helpdeskspringboot.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Objects;

@Controller
public class TicketController {
    private final TicketService ticketService;
    private final CommentService commentService;
    private final UserRepository userRepository;


    public TicketController(TicketService ticketService,
                            CommentService commentService,
                            UserRepository userRepository) {
        this.ticketService = ticketService;
        this.commentService = commentService;
        this.userRepository = userRepository;
    }


    @GetMapping("/tickets")
    public String showTickets(Principal principal, Model model) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));

        model.addAttribute("userId", user.getId());

        if (user.getRole() == User.Role.AGENT) {
            model.addAttribute("tickets", ticketService.getAllTickets());
        } else if (user.getRole() == User.Role.USER) {
            model.addAttribute("tickets", ticketService.getTicketsByUserId(user.getId()));
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return "tickets";
    }

    @PostMapping("/tickets")
    public String createTicket(@RequestParam("title") String title,
                               @RequestParam("description") String description,
                               Principal principal) {
        Long userId = getLoggedInUserId(principal);
        ticketService.createTicket(title, description, userId);
        return "redirect:/tickets";
    }

    private Long getLoggedInUserId(Principal principal) {
        return userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new IllegalStateException(
                        "Den inloggade användaren finns inte i databasen"))
                .getId();
    }

    @GetMapping("/tickets/{id}")
    public String showTicket(@PathVariable("id") Long id,
                             Principal principal,
                             Model model) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));

        Ticket ticket = ticketService.getTicketById(id);

        boolean isAgent = user.getRole() == User.Role.AGENT;
        boolean isOwner = user.getRole() == User.Role.USER
                && ticket.getCreatedBy() != null
                && Objects.equals(ticket.getCreatedBy().getId(), user.getId());

        if (!isAgent && !isOwner) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        model.addAttribute("ticket", ticket);
        model.addAttribute("comments", commentService.getCommentsByTicketId(id));
        model.addAttribute("isAgent", isAgent);
        model.addAttribute("statuses", TicketStatus.values());
        return "ticket-detail";
    }

    @PostMapping("/tickets/{id}/assign")
    public String assignTicket(@PathVariable("id") Long id, Principal principal) {
        ticketService.assignTicketToSelf(id, principal.getName());
        return "redirect:/tickets/" + id;
    }

    @PostMapping("/tickets/{id}/status")
    public String updateTicketStatus(@PathVariable("id") Long id,
                                     @RequestParam("status") TicketStatus status,
                                     Principal principal) {
        ticketService.updateTicketStatus(id, status, principal.getName());
        return "redirect:/tickets/" + id;
    }

}
