package com.sigsauer.univ.abc.service.impl;

import com.sigsauer.univ.abc.controllers.LegalClientController;
import com.sigsauer.univ.abc.models.clients.NaturalClient;
import com.sigsauer.univ.abc.models.communication.Email;
import com.sigsauer.univ.abc.repository.EmailRepository;
import com.sigsauer.univ.abc.repository.NaturalClientRepository;
import com.sigsauer.univ.abc.service.EmailService;
import com.sigsauer.univ.abc.service.NaturalClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger log = LoggerFactory.getLogger(LegalClientController.class);

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private NaturalClientService ncService;

    private final String host = "smtp.ukr.net";
    private final String port = "465";
    private final String login = "admin.lms@ukr.net";
    private final String password = "rQwhyuEzr1dzLD0d";


    public boolean sendEmail(Email email) {
        if (email.getRecipient().getEmail().isEmpty()) return false;

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host",host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(login, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(login));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email.getRecipient().getEmail()));
            message.setSubject(email.getSubject());
            message.setText(email.getBody());

            Transport.send(message);
            if (email.getSubject().isEmpty()) email.setSubject("<no subject>");
            emailRepository.save(email);
            log.info("Email SENT: {}, {}, {}. ", email.getRecipient().getEmail(), email.getSubject(), email.getBody());
            return true;
        } catch (MessagingException e) {
            log.error("ERROR during sending email: {}, {}, {}. ", email.getRecipient().getEmail(), email.getSubject(), email.getBody(), e);
            return false;
        }
    }

    @Override
    public List<Email> getEmailsByClientId(Long id) {
        NaturalClient nc = ncService.findById(id);
        return emailRepository.findAllByRecipientEquals(nc);
    }

}
