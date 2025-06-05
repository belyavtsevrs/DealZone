package com.epam.dealzone.config;

import com.epam.dealzone.domain.entity.Customer;
import com.epam.dealzone.domain.enums.Role;
import com.epam.dealzone.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Set;
import java.util.UUID;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/productImages/**")
                .addResourceLocations("file:upload/productImages/");

        registry.addResourceHandler("/upload/customerAvatars/**")
                .addResourceLocations("file:upload/customerAvatars/");

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
    @Bean
    public CommandLineRunner loadAdmin(
            CustomerRepository customerRepository,
            PasswordEncoder passwordEncoder){
        return args ->{
            if(!customerRepository.findCustomerByEmail("admin@admin.com").isPresent()){
                Customer admin =
                        Customer.builder()
                                .name("Admin")
                                .surname("Admin")
                                .email("admin@admin.com")
                                .password(passwordEncoder.encode("admin"))
                                .city("adminsk")
                                .phoneNumber("8-800-555-35-35")
                                .role(Set.of(Role.ADMIN))
                                .active(true)
                                .avatar_url("no-user-image.jpg")
                                .build();
                customerRepository.save(admin);
            }
        };
    }
}
