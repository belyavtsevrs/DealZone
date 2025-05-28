package com.epam.dealzone.service;

import com.epam.dealzone.domain.entity.Product;
import com.epam.dealzone.service.api.Deleter;
import com.epam.dealzone.service.api.Retriever;
import com.epam.dealzone.service.api.Saver;
import com.epam.dealzone.service.api.Updater;
import com.epam.dealzone.web.dto.ProductRequest;
import com.epam.dealzone.web.dto.ProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService extends
        Saver<ProductRequest>,
        Retriever<ProductResponse,UUID>, Updater<ProductRequest,UUID>, Deleter<UUID> {

}
