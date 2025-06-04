package com.epam.dealzone.web.controller.impl;

import com.epam.dealzone.domain.entity.Customer;
import com.epam.dealzone.service.api.BannerUser;
import com.epam.dealzone.service.api.Deleter;
import com.epam.dealzone.service.api.Retriever;
import com.epam.dealzone.service.api.Updater;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@PreAuthorize("hasAuthority('ADMIN_ROLE')")
@RequestMapping("/admin")
public class AdminController {
    private final Retriever<Customer, UUID> retriever;
    private final Deleter<UUID> deleter;
    private final Updater<Customer,UUID> updater;
    private final BannerUser<UUID> bannerUser;
    public AdminController(
            @Qualifier("adminServiceImpl") Retriever<Customer, UUID> retriever,
            @Qualifier("adminServiceImpl") Deleter<UUID> deleter,
            @Qualifier("adminServiceImpl") Updater<Customer, UUID> updater,
            BannerUser<UUID> bannerUser) {
        this.retriever = retriever;
        this.deleter = deleter;
        this.updater = updater;
        this.bannerUser = bannerUser;
    }

    @GetMapping("/panel")
    public String getAdminPanel(Customer customer, Model model){
        model.addAttribute("customers",retriever.retrieve());
        return "AdminPanel";
    }

    @GetMapping("/delete/{uuid}")
    public String deleteUser(@PathVariable("uuid")UUID uuid){
        deleter.deleter(uuid);
        return "redirect:/admin/panel";
    }

    @GetMapping("/ban/{uuid}")
    public String ban(@PathVariable("uuid")UUID uuid){
        bannerUser.banUser(uuid);
        return "redirect:/admin/panel";
    }
}
