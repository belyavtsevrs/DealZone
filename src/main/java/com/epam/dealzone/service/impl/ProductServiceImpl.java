package com.epam.dealzone.service.impl;

import com.epam.dealzone.domain.entity.Product;
import com.epam.dealzone.repository.ProductRepository;
import com.epam.dealzone.service.ProductService;
import com.epam.dealzone.web.dto.ProductRequest;
import com.epam.dealzone.web.dto.ProductResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(ProductRequest request) {
        return productRepository.save(request.toProduct());
    }

    @Override
    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(x-> ProductResponse.fromProduct(x))
                .toList();
    }
}
