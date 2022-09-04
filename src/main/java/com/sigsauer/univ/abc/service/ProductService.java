package com.sigsauer.univ.abc.service;

import com.sigsauer.univ.abc.models.communication.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    List<Product> getProductsByClientId(Long id);
}
