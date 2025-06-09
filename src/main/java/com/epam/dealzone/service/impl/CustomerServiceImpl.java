package com.epam.dealzone.service.impl;

import com.epam.dealzone.domain.entity.Customer;
import com.epam.dealzone.domain.entity.Product;
import com.epam.dealzone.domain.enums.Role;
import com.epam.dealzone.repository.CustomerRepository;
import com.epam.dealzone.repository.ProductRepository;
import com.epam.dealzone.service.CustomerService;
import com.epam.dealzone.web.dto.CustomerRequest;
import com.epam.dealzone.web.dto.CustomerResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    private static final String imageDir = "customerAvatars";
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageServiceImpl fileStorageService;
    private final ProductRepository productRepository;
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               PasswordEncoder passwordEncoder,
                               FileStorageServiceImpl fileStorageService,
                               ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.fileStorageService = fileStorageService;
        this.productRepository = productRepository;
    }

    @Override
    public void deleter(UUID uuid) {
        if(!customerRepository.existsById(uuid)){
            throw new RuntimeException();
        }
        customerRepository.deleteById(uuid);
    }

    @Override
    public List<CustomerResponse> retrieve() {
        throw new UnsupportedOperationException();
    }


    @Override
    public CustomerResponse retrieve(UUID uuid) {
        Customer customer = customerRepository.findById(uuid).orElseThrow(()->{
            throw new RuntimeException();
        });
        return CustomerResponse.toResponse(customer);
    }

    @Override
    public CustomerResponse retrieve(String name) {
        Customer customer = customerRepository.findCustomerByEmail(name).orElseThrow(()->{
            throw new RuntimeException();
        });
        return CustomerResponse.toResponse(customer);
    }

    @Override
    public Page<CustomerResponse> retrieve(String search, Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void saver(CustomerRequest request) {
        log.info("request = {}", request);
        Customer customer = Customer.builder()
                .email(request.getEmail())
                .name(request.getName())
                .surname(request.getSurname())
                .city(request.getCity())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .active(true)
                .role(Set.of(Role.USER))
                .avatar_url("no-user-image.jpg")
                .build();

        log.info("customer = {}",customer);
        customerRepository.save(customer);
    }

    @Override
    public void updater(CustomerRequest request, UUID uuid) {
        Customer customer = customerRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Customer not found with uuid: " + uuid));

        String url = request.getNewAvatar().isEmpty()
                ? customer.getAvatar_url()
                : fileStorageService.saveFile(request.getNewAvatar(), imageDir);

        Customer updated = Customer.builder()
                .uuid(uuid)
                .email(customer.getEmail())
                .password(customer.getPassword())
                .active(customer.isActive())
                .name(request.getName())
                .surname(request.getSurname())
                .phoneNumber(request.getPhoneNumber())
                .city(request.getCity())
                .avatar_url(url)
                .creationDate(customer.getCreationDate())
                .products(customer.getProducts())
                .favouriteProducts(customer.getFavouriteProducts())
                .build();

        customerRepository.save(updated);
    }

    @Override
    public void addFavorite(UUID cId, UUID pId) {
        Customer customer = customerRepository.findById(cId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Product product = productRepository.findById(pId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        customer.getFavouriteProducts().add(product);
        log.info("favorites = {}",customer.getFavouriteProducts());
        customerRepository.save(customer);
    }
}
