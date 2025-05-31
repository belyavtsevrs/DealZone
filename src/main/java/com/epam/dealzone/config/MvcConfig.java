package com.epam.dealzone.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/productImages/**")
                .addResourceLocations("file:upload/productImages/");

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}
