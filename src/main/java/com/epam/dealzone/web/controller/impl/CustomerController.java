package com.epam.dealzone.web.controller.impl;

import com.epam.dealzone.domain.entity.Customer;
import com.epam.dealzone.repository.CustomerRepository;
import com.epam.dealzone.security.UserDetailsImpl;
import com.epam.dealzone.service.api.Favoriter;
import com.epam.dealzone.service.api.Retriever;
import com.epam.dealzone.service.api.Updater;
import com.epam.dealzone.web.dto.CustomerRequest;
import com.epam.dealzone.web.dto.CustomerResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    private final Retriever<CustomerResponse, UUID> retriever;
    private final Updater<CustomerRequest,UUID> updater;
    private final Favoriter<UUID,UUID> favoriter;
    private final CustomerRepository customerRepository;

    public CustomerController(
            @Qualifier("customerServiceImpl") Retriever<CustomerResponse, UUID> retriever,
            @Qualifier("customerServiceImpl")Updater<CustomerRequest, UUID> updater,
            Favoriter<UUID, UUID> favoriter, CustomerRepository customerRepository) {
        this.retriever = retriever;
        this.updater = updater;
        this.favoriter = favoriter;
        this.customerRepository = customerRepository;
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

    @PostMapping("/favorite/{uuid}")
    public String favorite(@PathVariable("uuid") UUID uuid, Principal principal) {
        Customer customer = customerRepository.findCustomerByEmail(principal.getName()).get();
        favoriter.addFavorite(customer.getUuid(),uuid);
        return "redirect:/products/product-info/" + uuid;
    }
}
