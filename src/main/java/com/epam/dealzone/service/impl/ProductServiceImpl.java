package com.epam.dealzone.service.impl;

import com.epam.dealzone.domain.entity.Customer;
import com.epam.dealzone.domain.entity.Image;
import com.epam.dealzone.domain.entity.Product;
import com.epam.dealzone.repository.CustomerRepository;
import com.epam.dealzone.repository.ProductRepository;
import com.epam.dealzone.service.FileStorageService;
import com.epam.dealzone.service.ProductService;
import com.epam.dealzone.service.api.Paginator;
import com.epam.dealzone.web.dto.ProductRequest;
import com.epam.dealzone.web.dto.ProductResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    private static final String imageDir = "productImages";
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final FileStorageServiceImpl storageService;
    private final FileStorageService fileStorageService;

    public ProductServiceImpl(ProductRepository productRepository,
                              CustomerRepository customerRepository,
                              FileStorageServiceImpl storageService, FileStorageService fileStorageService) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.storageService = storageService;
        this.fileStorageService = fileStorageService;
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
                log.info("imageUrl = {}",imagePath);
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
    public Page<ProductResponse> retrieve(String search, Pageable pageable) {
        if(search == null || search.isBlank()){
            return productRepository.findAll(pageable)
                    .map(ProductResponse::toResponse);
        }
        return productRepository.findAllByTitleIgnoreCase(search,pageable)
                .map(ProductResponse::toResponse);
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

        List<MultipartFile> imagesList = request.getImages().stream()
                .filter(Objects::nonNull)
                .filter(image -> !image.isEmpty())
                .toList();

        int existImages = existingProduct.getImages().size();
        int requestImages = imagesList.size();

        for (int i = 0; i < requestImages; i++) {
            String url = storageService.saveFile(imagesList.get(i), imageDir);
            Image newImage = Image.builder()
                    .url(url)
                    .product(existingProduct)
                    .build();

            if (existImages < 3) {
                existingProduct.getImages().add(newImage);
                existImages++;
            } else {
                existingProduct.getImages().set(3 - requestImages + i, newImage);
            }
        }

        Product updated = Product.builder()
                        .uuid(uuid)
                        .title(request.getTitle())
                        .description(request.getDescription())
                        .price(request.getPrice())
                        .images(existingProduct.getImages())
                        .creationDate(existingProduct.getCreationDate())
                        .customer(existingProduct.getCustomer())
                        .build();

        productRepository.save(updated);
    }

}
