package org.example.helpdeskspringboot.controller;


import org.example.helpdeskspringboot.service.CommentService;
import org.example.helpdeskspringboot.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TicketController {
    private final TicketService ticketService;
    private final CommentService commentService;

    public TicketController(TicketService ticketService,
                            CommentService commentService) {
        this.ticketService = ticketService;
        this.commentService = commentService;
    }


    @GetMapping("/tickets")
    public String showTickets(@RequestParam("userId") Long userId,
                              Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("tickets",
                ticketService.getTicketsByUserId(userId));
        return "tickets";
    }

    @PostMapping("/tickets")
    public String createTicket(@RequestParam("title") String title,
                               @RequestParam("description") String description,
                               @RequestParam("userId") Long userId) {
        ticketService.createTicket(title, description, userId);

        return "redirect:/tickets?userId=" + userId;
    }


    @GetMapping("/tickets/{id}")
    public String showTicket(@PathVariable("id") Long id, Model model) {
        model.addAttribute("ticket", ticketService.getTicketById(id));
        model.addAttribute("comments", commentService.getCommentsByTicketId(id));
        return "ticket-detail";
    }

}
