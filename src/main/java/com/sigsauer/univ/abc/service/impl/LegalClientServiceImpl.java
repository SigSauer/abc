package com.sigsauer.univ.abc.service.impl;

import com.sigsauer.univ.abc.models.clients.LegalClient;
import com.sigsauer.univ.abc.models.exceptions.LegalClientAlreadyExistException;
import com.sigsauer.univ.abc.models.exceptions.LegalClientIsBlockedException;
import com.sigsauer.univ.abc.models.exceptions.LegalClientNotFoundException;
import com.sigsauer.univ.abc.repository.LegalClientRepository;
import com.sigsauer.univ.abc.service.LegalClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LegalClientServiceImpl implements LegalClientService {

    @Autowired
    LegalClientRepository lcRepository;

    @Override
    public List<LegalClient> findAll() {
        return lcRepository.findAll();
    }

    @Override
    public LegalClient findById(Long id) {
        return lcRepository.findById(id).
                orElseThrow(() -> new LegalClientNotFoundException(id));
    }

    @Override
    public LegalClient add(LegalClient lc) throws LegalClientAlreadyExistException{
        if(lcRepository.existsByEdrpou(lc.getEdrpou()))
            throw new LegalClientAlreadyExistException("EDRPOU", lc.getEdrpou());
        lc.setActive(true);
        return lcRepository.save(lc);

    }

    @Override
    public LegalClient update(Long id, LegalClient lc)
            throws LegalClientIsBlockedException, LegalClientAlreadyExistException{
        LegalClient old = findById(id);
        if(lcRepository.existsByIdAndActiveFalse(id))
            throw new LegalClientIsBlockedException(id);
        if(!old.getEdrpou().equals(lc.getEdrpou()) && lcRepository.existsByEdrpou(lc.getEdrpou()))
            throw new LegalClientAlreadyExistException("EDRPOU", lc.getEdrpou());
        lc.setId(old.getId());
        lc.setActive(true);
        return lcRepository.save(lc);
    }

    @Override
    public LegalClient block(Long id) throws LegalClientNotFoundException{
        LegalClient lc = findById(id);
        lc.setActive(false);
        return lcRepository.save(lc);
    }
}
