package com.epam.dealzone.service;

import com.epam.dealzone.domain.entity.Product;
import com.epam.dealzone.web.dto.ProductRequest;
import com.epam.dealzone.web.dto.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {
    Product createProduct(final ProductRequest request);
    List<ProductResponse> findAll();
}
