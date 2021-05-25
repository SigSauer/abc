package com.sigsauer.univ.abc.service.impl;

import com.sigsauer.univ.abc.models.clients.NaturalClient;
import com.sigsauer.univ.abc.models.exceptions.NaturalClientAlreadyExistException;
import com.sigsauer.univ.abc.models.exceptions.NaturalClientIsBlockedException;
import com.sigsauer.univ.abc.models.exceptions.NaturalClientNotFoundException;
import com.sigsauer.univ.abc.repository.NaturalClientRepository;
import com.sigsauer.univ.abc.service.NaturalClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NaturalClientServiceImpl implements NaturalClientService {

    @Autowired
    NaturalClientRepository ncRepository;


    @Override
    public List<NaturalClient> findAll() {
        return ncRepository.findAll();
    }

    @Override
    public NaturalClient findById(Long id) {
        return ncRepository.findById(id).
                orElseThrow(() -> new NaturalClientNotFoundException(id));
    }

    @Override
    public NaturalClient add(NaturalClient nc) throws NaturalClientAlreadyExistException {
        if(ncRepository.existsByMobile(nc.getMobile()))
            throw new NaturalClientAlreadyExistException("mobile",nc.getMobile());
        else if(ncRepository.existsByTin(nc.getTin()))
            throw new NaturalClientAlreadyExistException("tin",nc.getTin());
        else if(ncRepository.existsByDocNumber(nc.getDocNumber()))
            throw new NaturalClientAlreadyExistException("docNumber",nc.getDocNumber());
        else {
            nc.setActive(true);
            return ncRepository.save(nc);
        }
        }


    @Override
    public NaturalClient update(Long id, NaturalClient nc)
            throws NaturalClientIsBlockedException, NaturalClientAlreadyExistException{
        NaturalClient old = findById(id);
        if(ncRepository.existsByIdAndActiveFalse(id))
            throw new NaturalClientIsBlockedException(id);
        else if(!old.getMobile().equals(nc.getMobile()) && ncRepository.existsByMobile(nc.getMobile()))
            throw new NaturalClientAlreadyExistException("mobile",nc.getMobile());
        else if(!old.getTin().equals(nc.getTin()) && ncRepository.existsByTin(nc.getTin()))
            throw new NaturalClientAlreadyExistException("tin",nc.getTin());
        else if(!old.getDocNumber().equals(nc.getDocNumber()) && ncRepository.existsByDocNumber(nc.getDocNumber()))
            throw new NaturalClientAlreadyExistException("docNumber",nc.getDocNumber());
        else {
            nc.setId(old.getId());
            nc.setActive(true);
            return ncRepository.save(nc);
        }
    }

    @Override
    public NaturalClient block(Long id) throws NaturalClientNotFoundException{
        NaturalClient nc = findById(id);
        nc.setActive(false);
        return ncRepository.save(nc);
    }

}
