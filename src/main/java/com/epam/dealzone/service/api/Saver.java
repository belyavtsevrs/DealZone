package com.epam.dealzone.service.api;

import com.epam.dealzone.domain.entity.Product;
import com.epam.dealzone.web.dto.ProductRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface Saver<E> {
    void saver(E e);
    void createWithImage(E e, List<MultipartFile> images);
}
