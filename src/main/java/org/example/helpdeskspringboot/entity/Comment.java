package org.example.helpdeskspringboot.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 5000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    public Comment() {

    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
    public Ticket getTicket() {
        return ticket;
    }
    public User getAuthor() {
        return author;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
    public void setAuthor(User author) {
        this.author = author;
    }

}
