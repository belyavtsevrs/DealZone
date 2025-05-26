package com.epam.dealzone.web.controller;

import com.epam.dealzone.domain.entity.Product;
import com.epam.dealzone.service.ProductService;
import com.epam.dealzone.web.dto.ProductRequest;
import com.epam.dealzone.web.dto.ProductResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/api/products/")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/")
    public String createProduct(@ModelAttribute ProductRequest productRequest){
        productService.createProduct(productRequest);
        return "redirect:/api/products/";
    }

    @GetMapping("/")
    public String findALL(Model model){
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
