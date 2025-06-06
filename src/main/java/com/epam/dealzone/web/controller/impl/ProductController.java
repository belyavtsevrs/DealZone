package com.epam.dealzone.web.controller.impl;

import com.epam.dealzone.domain.entity.Customer;
import com.epam.dealzone.service.CustomerService;
import com.epam.dealzone.service.ProductService;
import com.epam.dealzone.service.api.*;
import com.epam.dealzone.service.impl.ProductServiceImpl;
import com.epam.dealzone.web.dto.CustomerRequest;
import com.epam.dealzone.web.dto.CustomerResponse;
import com.epam.dealzone.web.dto.ProductRequest;
import com.epam.dealzone.web.dto.ProductResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/products/")
public class ProductController {
    private final Retriever<ProductResponse,UUID> retriever;
    private final Retriever<CustomerResponse,UUID> customerRetriever;
    private final Deleter<UUID> deleter;
    private final Updater<ProductRequest,UUID> updater;
    private final Saver<ProductRequest> saver;

    public ProductController(
            @Qualifier("productServiceImpl") Retriever<ProductResponse,UUID> retriever,
            @Qualifier("customerServiceImpl") Retriever<CustomerResponse, UUID> customerRetriever,
            @Qualifier("productServiceImpl") Deleter<UUID> deleter,
            @Qualifier("productServiceImpl") Updater<ProductRequest,UUID> updater,
            @Qualifier("productServiceImpl") ProductService productService,
            ProductServiceImpl productServiceImpl, Saver<ProductRequest> saver) {
        this.retriever = retriever;
        this.customerRetriever = customerRetriever;
        this.deleter = deleter;
        this.updater = updater;
        this.saver = saver;
    }

    @GetMapping("/sell-product")
    public String createProduct(Model model) {
        model.addAttribute("request", new ProductRequest());
        return "productCreationForm";
    }


    @PostMapping("/sell-product")
    public String createProduct(
            @ModelAttribute ProductRequest productRequest,Principal principal) {
        productRequest.setPrincipalName(principal.getName());
        saver.saver(productRequest);
        return "redirect:/products/";
    }

    @GetMapping("/")
    public String findAll(
            @RequestParam(value = "search", required = false) String search,
            @PageableDefault(size = 5, page = 0) Pageable pageable,
            Model model,
            Principal principal) {

        Page<ProductResponse> page = retriever.retrieve(search, pageable);

        model.addAttribute("productList", page.getContent());
        model.addAttribute("page", page);
        model.addAttribute("search", search);
        if (principal != null) {
            model.addAttribute("customer", customerRetriever.retrieve(principal.getName()));
        }
        model.addAttribute("userPrincipal", principal);

        return "products";
    }

    @GetMapping("/product-info/{uuid}")
    public String productInfo(@PathVariable("uuid")UUID uuid, Model model,Principal principal){
        ProductResponse product = retriever.retrieve(uuid);
        String principalName = principal != null ? principal.getName() : "";
        model.addAttribute("product",product);
        model.addAttribute("productRequest",product.toRequest());
        model.addAttribute("productOwner",product.getEmailOwner());
        model.addAttribute("currentUser",principalName);
        return "productInfo";
    }

    @PostMapping("productInfo/update-product/{uuid}")
    public String updateCustomer(@PathVariable("uuid") UUID uuid, ProductRequest request) {
        updater.updater(request, uuid);
        return "redirect:/products/product-info/" + uuid;
    }

    @GetMapping("/delete/{uuid}")
    public String deleteProduct(@PathVariable("uuid")UUID uuid){
        deleter.deleter(uuid);
        return "redirect:/products/";
    }
}
