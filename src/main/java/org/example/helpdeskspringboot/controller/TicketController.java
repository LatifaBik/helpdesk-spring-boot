package org.example.helpdeskspringboot.controller;


import org.example.helpdeskspringboot.repository.UserRepository;
import org.example.helpdeskspringboot.service.CommentService;
import org.example.helpdeskspringboot.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

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
        Long userId = getLoggedInUserId(principal);
        model.addAttribute("userId", userId);
        model.addAttribute("tickets", ticketService.getTicketsByUserId(userId));
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
    public String showTicket(@PathVariable("id") Long id, Model model) {
        model.addAttribute("ticket", ticketService.getTicketById(id));
        model.addAttribute("comments", commentService.getCommentsByTicketId(id));
        return "ticket-detail";
    }

}
