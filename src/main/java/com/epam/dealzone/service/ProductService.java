package com.epam.dealzone.service;

import com.epam.dealzone.service.api.*;
import com.epam.dealzone.web.dto.ProductRequest;
import com.epam.dealzone.web.dto.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ProductService extends
        Saver<ProductRequest>,
        Retriever<ProductResponse,UUID>,
        Updater<ProductRequest,UUID>,
        Deleter<UUID>
{
    void createWithImage(ProductRequest request, List<MultipartFile> images);
}
