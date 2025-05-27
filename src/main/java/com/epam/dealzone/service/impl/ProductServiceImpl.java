package com.epam.dealzone.service.impl;

import com.epam.dealzone.domain.entity.Image;
import com.epam.dealzone.domain.entity.Product;
import com.epam.dealzone.repository.ProductRepository;
import com.epam.dealzone.service.ProductService;
import com.epam.dealzone.web.dto.ProductRequest;
import com.epam.dealzone.web.dto.ProductResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final FileStorageServiceImpl storageService;
    private static final String imageDir = "productImages";

    public ProductServiceImpl(ProductRepository productRepository,
                              FileStorageServiceImpl storageService) {
        this.productRepository = productRepository;
        this.storageService = storageService;
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

    @Override
    public ProductResponse findById(UUID uuid) {
        Product product = productRepository.findById(uuid)
                .orElseThrow(()->{
                    throw new RuntimeException("not found ");
                });
        ProductResponse response = ProductResponse.fromProduct(product);
        log.info("response = {}" , response);
        return response;
    }

    @Override
    @Transactional
    public Product createProductWithImage(ProductRequest request, List<MultipartFile> images) {
        try {
            Product product = request.toProduct();
            for(int i = 0; i < images.size();i++){
                String url = storageService.saveFile(images.get(i),imageDir);
                Image image = Image.builder()
                        .url(url)
                        .isPreview(i == 0 ? true : false)
                        .build();
                product.addImage(image);
            }
            log.info("product = {}" , product);
            return productRepository.save(product);
        }catch (Exception e) {
            throw new RuntimeException("Failed to create product with images", e);
        }
    }
}
