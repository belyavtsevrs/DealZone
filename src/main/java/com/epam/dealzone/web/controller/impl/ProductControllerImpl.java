package com.epam.dealzone.web.controller.impl;

import com.epam.dealzone.service.ProductService;
import com.epam.dealzone.service.api.Deleter;
import com.epam.dealzone.service.api.Retriever;
import com.epam.dealzone.service.api.Saver;
import com.epam.dealzone.service.api.Updater;
import com.epam.dealzone.web.dto.ProductRequest;
import com.epam.dealzone.web.dto.ProductResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/api/products/")
public class ProductControllerImpl {
    private final Retriever<ProductResponse,UUID> retriever;
    private final Deleter<UUID> deleter;
    private final Updater<ProductRequest,UUID> updater;
    private final Saver<ProductRequest> saver;

    public ProductControllerImpl(Retriever<ProductResponse, UUID> retriever, Deleter<UUID> deleter, Updater<ProductRequest, UUID> updater, Saver<ProductRequest> saver) {
        this.retriever = retriever;
        this.deleter = deleter;
        this.updater = updater;
        this.saver = saver;
    }


    @PostMapping()
    public String createProduct(
            @ModelAttribute ProductRequest productRequest,
            @RequestParam("images") List<MultipartFile> images
    ) {
        saver.createWithImage(productRequest, images);
        return "redirect:/api/products/";
    }

    @GetMapping("/")
    public String findAll(Model model){
        model.addAttribute("request", new ProductRequest());
        model.addAttribute("productList",retriever.retrieve());
        return "products";
    }

    @GetMapping("/product-info/{uuid}")
    public String productInfo(@PathVariable("uuid")UUID uuid, Model model){
        model.addAttribute("product",retriever.retrieve(uuid));
        return "productInfo";
    }

    @GetMapping("/delete/{uuid}")
    public String deleteProduct(@PathVariable("uuid")UUID uuid){
        deleter.deleter(uuid);
        return "redirect:/api/products/";
    }
}
