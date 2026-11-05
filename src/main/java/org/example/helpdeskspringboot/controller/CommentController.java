package org.example.helpdeskspringboot.controller;

import org.example.helpdeskspringboot.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/tickets/{id}/comments")
    public String addComment(@PathVariable("id") Long ticketId,
                             @RequestParam("content") String content,
                             @RequestParam("userId") Long userId) {
        commentService.addComment(content, ticketId, userId);
        return "redirect:/tickets/" + ticketId;
    }
}
