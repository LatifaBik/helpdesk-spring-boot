package org.example.helpdeskspringboot.controller;

import org.example.helpdeskspringboot.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TicketController {
    private final TicketService ticketService;
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
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

}
