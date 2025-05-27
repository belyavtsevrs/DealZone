package com.epam.dealzone.web.controller.impl;

import com.epam.dealzone.service.ProductService;
import com.epam.dealzone.web.dto.ProductRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/api/products/")
public class ProductControllerImpl {
    private final ProductService productService;

    public ProductControllerImpl(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/")
    public String createProduct(
            @ModelAttribute ProductRequest productRequest,
            @RequestParam("images") List<MultipartFile> images
    ) {
        productService.createProductWithImage(productRequest, images);
        return "redirect:/api/products/";
    }

    @GetMapping("/")
    public String findAll(Model model){
        model.addAttribute("request", new ProductRequest());
        model.addAttribute("productList",productService.findAll());
        return "products";
    }

    @GetMapping("/product-info/{uuid}")
    public String productInfo(@PathVariable("uuid")UUID uuid, Model model){
        model.addAttribute("product",productService.findById(uuid));
        return "productInfo";
    }
}
