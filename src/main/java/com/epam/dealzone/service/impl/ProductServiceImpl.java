package com.epam.dealzone.service.impl;

import com.epam.dealzone.domain.entity.Customer;
import com.epam.dealzone.domain.entity.Image;
import com.epam.dealzone.domain.entity.Product;
import com.epam.dealzone.repository.CustomerRepository;
import com.epam.dealzone.repository.ProductRepository;
import com.epam.dealzone.service.ProductService;
import com.epam.dealzone.web.dto.ProductRequest;
import com.epam.dealzone.web.dto.ProductResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    private static final String imageDir = "productImages";

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final FileStorageServiceImpl storageService;

    public ProductServiceImpl(ProductRepository productRepository,
                              CustomerRepository customerRepository,
                              FileStorageServiceImpl storageService) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.storageService = storageService;
    }

    @Override
    @Transactional
    public void createWithImage(ProductRequest request, List<MultipartFile> images) {
        List<MultipartFile> imagesList = images.stream()
                .filter(image -> !image.isEmpty())
                .toList();
        Customer owner = customerRepository.findCustomerByEmail(request.getPrincipalName())
                .orElseThrow(()->{
                    throw new RuntimeException("user not found");
                });
        try {
            Product product = request.toProduct();
            for(int i = 0; i < imagesList.size();i++){
                String url = storageService.saveFile(imagesList.get(i),imageDir);
                Image image = Image.builder()
                        .url(url)
                        .isPreview(i == 0 ? true : false)
                        .build();
                product.addImage(image);
            }
            product.setCustomer(owner);
            productRepository.save(product);
            log.info("product = {} has bees saved" , product);
        }catch (Exception e) {
            throw new RuntimeException("Failed to create product with images", e);
        }
    }

    @Override
    public void deleter(UUID uuid) {
        Product product = productRepository.findById(uuid)
                .orElseThrow(()->{
                    throw new RuntimeException();
                });
        for (Image image : product.getImages()) {
            try {
                Path imagePath = Path.of(imageDir,image.getUrl());
                Files.deleteIfExists(imagePath);
            } catch (IOException e) {
                log.warn("Failed to delete image: {}", image.getUrl(), e);
            }
        }
        productRepository.deleteById(uuid);
        log.info("product has been deleted");
    }

    @Override
    public List<ProductResponse> retrieve() {
        return productRepository.findAll()
                .stream()
                .map(x-> ProductResponse.toResponse(x))
                .toList();
    }

    @Override
    public List<ProductResponse> retrieve(int page, int size) {
        return List.of();
    }

    @Override
    public ProductResponse retrieve(UUID uuid) {
        Product product = productRepository.findById(uuid)
                .orElseThrow(()->{
                    throw new RuntimeException("not found ");
                });
        ProductResponse response = ProductResponse.toResponse(product);
        log.info("response = {}" , response);
        return response;
    }

    @Override
    public ProductResponse retrieve(String name) {
        return null;
    }

    @Override
    public void saver(ProductRequest request) {
        List<MultipartFile> imagesList = request.getImages().stream()
                .filter(Objects::nonNull)
                .filter(image -> !image.isEmpty())
                .toList();

        Customer owner = customerRepository.findCustomerByEmail(request.getPrincipalName())
                .orElseThrow(()->{
                    throw new RuntimeException("user not found");
                });

        try {
            Product product = request.toProduct();
            for(int i = 0; i < imagesList.size();i++){
                String url = storageService.saveFile(imagesList.get(i),imageDir);
                Image image = Image.builder()
                        .url(url)
                        .isPreview(i == 0 ? true : false)
                        .build();
                product.addImage(image);
            }
            product.setCustomer(owner);
            productRepository.save(product);
            log.info("product = {} has bees saved" , product);
        }catch (Exception e) {
            throw new RuntimeException("Failed to create product with images", e);
        }
    }

    @Override
    public void updater(ProductRequest request, UUID uuid) {
        Product existingProduct = productRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("not found" + uuid));

        Product product = Product.builder()
                .build();
        productRepository.save(existingProduct);
    }
}
