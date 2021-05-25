package com.sigsauer.univ.abc.service;

import com.sigsauer.univ.abc.models.clients.LegalClient;

import java.util.List;

public interface LegalClientService {

    List<LegalClient> findAll();

    LegalClient findById(Long id);

    LegalClient add(LegalClient lc);

    LegalClient update(Long id, LegalClient lc);

    LegalClient block(Long id);
}
