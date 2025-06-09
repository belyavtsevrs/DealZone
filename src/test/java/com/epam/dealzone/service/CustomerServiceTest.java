package com.epam.dealzone.service;

import com.epam.dealzone.domain.entity.Customer;
import com.epam.dealzone.domain.entity.Product;
import com.epam.dealzone.domain.enums.Role;
import com.epam.dealzone.repository.CustomerRepository;
import com.epam.dealzone.repository.ProductRepository;
import com.epam.dealzone.service.impl.CustomerServiceImpl;
import com.epam.dealzone.service.impl.FileStorageServiceImpl;
import com.epam.dealzone.web.dto.CustomerRequest;
import com.epam.dealzone.web.dto.CustomerResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private FileStorageServiceImpl fileStorageService;
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    CustomerServiceImpl customerService;

    Product product;
    CustomerRequest customerRequest;
    Customer customer;
    UUID uuid;
    @BeforeEach
    public void init(){
        customerRequest = new CustomerRequest();
        customerRequest.setEmail("qwert@gmail.com");
        customerRequest.setPassword("12345qwerty");
        customerRequest.setNewAvatar(new MockMultipartFile("image","image","image","image".getBytes()));
        customerRequest.setName("customerName");
        customerRequest.setSurname("surname");
        customerRequest.setPhoneNumber("88005553535");

        uuid = UUID.randomUUID();
        product = new Product();
        product.setCreationDate(LocalDateTime.now());
        product.setUuid(uuid);

        customer = Customer.builder()
                .uuid(uuid)
                .email("newUser@gmail.com")
                .password("password")
                .name("name")
                .surname("fwewfe")
                .city("city")
                .creationDate(LocalDateTime.now())
                .products(List.of(product))
                .build();

        product.setCustomer(customer);
    }

    @Test
    public void saver_success() {
        customerService.saver(customerRequest);
        ArgumentCaptor<Customer> res = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(res.capture());

        Customer newCustomer = res.getValue();

        assertEquals("qwert@gmail.com",newCustomer.getEmail());
        assertEquals("customerName",newCustomer.getName());
        assertEquals("surname",newCustomer.getSurname());
        assertEquals("88005553535",newCustomer.getPhoneNumber());
        assertEquals(Set.of(Role.USER),newCustomer.getRole());
        assertNotEquals("12345qwerty",newCustomer.getPassword());
        assertEquals("no-user-image.jpg",newCustomer.getAvatar_url());
    }

    @Test
    public void updater_success() {
        when(customerRepository.findById(uuid)).thenReturn(Optional.of(customer));
        when(fileStorageService.saveFile(eq(customerRequest.getNewAvatar()), anyString()))
                .thenReturn("newAvatar.jpg");

        customerService.updater(customerRequest,uuid);
        ArgumentCaptor<Customer> updated = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(updated.capture());

        Customer saved = updated.getValue();

        assertEquals(customerRequest.getName(), saved.getName());
        assertEquals(customerRequest.getSurname(), saved.getSurname());
        assertEquals(customerRequest.getPhoneNumber(), saved.getPhoneNumber());
        assertEquals("newAvatar.jpg", saved.getAvatar_url());
        assertEquals(customer.getEmail(), saved.getEmail());
    }

    @Test
    public void addFavorites(){
        when(customerRepository.findById(uuid)).thenReturn(Optional.of(customer));
        when(productRepository.findById(uuid)).thenReturn(Optional.of(product));

        customerService.addFavorite(customer.getUuid(),product.getUuid());
        verify(customerRepository).findById(uuid);
        verify(productRepository).findById(uuid);

        ArgumentCaptor<Customer> addedProduct = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(addedProduct.capture());

        Customer updated = addedProduct.getValue();
        assertEquals(1,updated.getFavouriteProducts().size());
    }

    @Test
    public void deleter(){
        when(customerRepository.existsById(uuid)).thenReturn(true);
        assertDoesNotThrow(()->
                customerService.deleter(uuid)
        );
        verify(customerRepository).existsById(uuid);
        verify(customerRepository).deleteById(uuid);
    }

    @Test
    public void retrieve(){
        when(customerRepository.findCustomerByEmail(customer.getEmail())).thenReturn(Optional.of(customer));

        CustomerResponse response = customerService.retrieve(customer.getEmail());

        assertNotNull(response);
        assertEquals(customer.getEmail(), response.getEmail());
        assertEquals(customer.getName(), response.getName());
        assertEquals(customer.getSurname(), response.getSurname());

        verify(customerRepository).findCustomerByEmail(customer.getEmail());
    }

    @Test
    public void retrieveAll(){

    }
}
