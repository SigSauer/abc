package com.sigsauer.univ.abc.service;

import com.sigsauer.univ.abc.models.communication.Email;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmailService {

    boolean sendEmail(Email email);

    List<Email> getEmailsByClientId(Long id);
}
