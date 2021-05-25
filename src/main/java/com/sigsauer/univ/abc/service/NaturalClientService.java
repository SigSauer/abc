package com.sigsauer.univ.abc.service;

import com.sigsauer.univ.abc.models.clients.NaturalClient;

import java.util.List;

public interface NaturalClientService {

    List<NaturalClient> findAll();

    NaturalClient findById(Long id);

    NaturalClient add(NaturalClient nc);

    NaturalClient update(Long id, NaturalClient nc);

    NaturalClient block(Long id);
}
