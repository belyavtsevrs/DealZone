package com.epam.dealzone.service;

import com.epam.dealzone.domain.entity.Product;
import com.epam.dealzone.web.dto.ProductRequest;
import com.epam.dealzone.web.dto.ProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    Product createProduct(final ProductRequest request);
    List<ProductResponse> findAll();
    ProductResponse findById(final UUID uuid);
    Product createProductWithImage(final ProductRequest request, List<MultipartFile> images);
}
