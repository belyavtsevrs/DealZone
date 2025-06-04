package com.epam.dealzone;

import com.epam.dealzone.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DealZoneApplication {

    public static void main(String[] args) {
        SpringApplication.run(DealZoneApplication.class, args);
    }

}
