package com.sigsauer.univ.abc.service.impl;

import com.sigsauer.univ.abc.models.clients.NaturalClient;
import com.sigsauer.univ.abc.models.communication.Product;
import com.sigsauer.univ.abc.repository.ProductRepository;
import com.sigsauer.univ.abc.service.NaturalClientService;
import com.sigsauer.univ.abc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    NaturalClientService ncService;
    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> getProductsByClientId(Long id) {
        NaturalClient nc = ncService.findById(id);
        return productRepository.findAllByClientsContains(nc);
    }
}
