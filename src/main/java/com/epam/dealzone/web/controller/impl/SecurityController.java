package com.epam.dealzone.web.controller.impl;

import com.epam.dealzone.service.CustomerService;
import com.epam.dealzone.web.dto.CustomerRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/security")
public class SecurityController {
    private final CustomerService customerService;

    public SecurityController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/login")
    public String loginForm(){
        return "login";
    }

    @GetMapping("/registration")
    public String registrationForm(Model model) {
        model.addAttribute("request",new CustomerRequest());
        return "registration";
    }

    @PostMapping("/register")
    public String register(CustomerRequest request){
        customerService.saver(request);
        return "redirect:/security/login";
    }
}
