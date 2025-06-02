package com.epam.dealzone.web.controller.impl;

import com.epam.dealzone.domain.entity.Customer;
import com.epam.dealzone.service.api.Retriever;
import com.epam.dealzone.service.api.Updater;
import com.epam.dealzone.web.dto.CustomerRequest;
import com.epam.dealzone.web.dto.CustomerResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.UUID;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    private final Retriever<CustomerResponse, UUID> retriever;
    private final Updater<CustomerRequest,UUID> updater;
    public CustomerController(
            @Qualifier("customerServiceImpl") Retriever<CustomerResponse, UUID> retriever,
            @Qualifier("customerServiceImpl")Updater<CustomerRequest, UUID> updater) {
        this.retriever = retriever;
        this.updater = updater;
    }

    @GetMapping("/profile/{param}")
    public String customerProfile(@PathVariable("param") String param, Model model, Principal principal){
        CustomerResponse customer;
        try {
            UUID uuid = UUID.fromString(param);
            customer = retriever.retrieve(uuid);
        } catch (IllegalArgumentException e) {
            customer = retriever.retrieve(param);
        }

        model.addAttribute("customer", customer);
        model.addAttribute("customerPrincipal", principal);
        model.addAttribute("customerRequest", customer.toRequest());
        return "сustomerProfile";
    }

    @PostMapping("/profile/update-profile/{uuid}")
    public String updateCustomer(@PathVariable("uuid") UUID uuid,CustomerRequest request){
        updater.updater(request,uuid);
        return "redirect:/customers/profile/"+uuid;
    }
}
