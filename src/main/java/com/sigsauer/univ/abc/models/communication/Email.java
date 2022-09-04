package com.sigsauer.univ.abc.models.communication;

import com.sigsauer.univ.abc.models.clients.NaturalClient;
import com.sigsauer.univ.abc.models.users.User;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "emails")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @OneToOne
    @JoinColumn(name = "recipient_id")
    private NaturalClient recipient;

    private String subject;

    private String body;

    private LocalDate creationDate;

    public Email(User sender, NaturalClient recipient, String subject, String body) {
        this.sender = sender;
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
        this.creationDate = LocalDate.now();
    }

    public Email() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public NaturalClient getRecipient() {
        return recipient;
    }

    public void setRecipient(NaturalClient recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Email{" +
                "sender=" + sender +
                ", recipient=" + recipient +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
