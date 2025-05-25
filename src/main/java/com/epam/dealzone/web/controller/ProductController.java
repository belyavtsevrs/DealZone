package com.epam.dealzone.web.controller;

import com.epam.dealzone.domain.entity.Product;
import com.epam.dealzone.service.ProductService;
import com.epam.dealzone.web.dto.ProductRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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

}
