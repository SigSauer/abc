package com.sigsauer.univ.abc.payload.request;

import javax.validation.constraints.NotBlank;

public class EmailRequest {

    @NotBlank
    private Long userId;

    @NotBlank
    private String recipient;

    private String subject;

    private String body;

    public EmailRequest(Long userId, String recipient, String subject, String body) {
        this.userId = userId;
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
    }

    public EmailRequest() {
    }

    public EmailRequest(Long userId) {
        this.userId = userId;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
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

    @Override
    public String toString() {
        return "EmailRequest{" +
                ", userId=" + userId +
                ", recipient='" + recipient + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
