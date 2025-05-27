package com.epam.dealzone.web.controller;

import com.epam.dealzone.web.dto.ProductRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ProductController {
    String createProduct(
            @ModelAttribute ProductRequest productRequest,
            @RequestParam("images") List<MultipartFile> images
    );
    public String productInfo(@PathVariable("uuid") UUID uuid, Model model);
    String findAll();

}
