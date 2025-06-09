package com.epam.dealzone.service;

import com.epam.dealzone.domain.entity.Customer;
import com.epam.dealzone.domain.entity.Product;
import com.epam.dealzone.repository.CustomerRepository;
import com.epam.dealzone.service.impl.AdminServiceImpl;
import com.epam.dealzone.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminServiceImplTest {
    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private AdminServiceImpl adminService;

    UUID uuid;
    Customer customer;
    Product product;

    @BeforeEach
    public void init(){
        uuid = UUID.randomUUID();

        product = Product.builder()
                .uuid(uuid)
                .build();

        customer = Customer.builder()
                .uuid(uuid)
                .email("newUser@gmail.com")
                .password("password")
                .name("name")
                .surname("fwewfe")
                .city("city")
                .active(true)
                .creationDate(LocalDateTime.now())
                .products(List.of(product))
                .build();
    }

    @Test
    public void banTest_success(){
        when(customerRepository.findById(customer.getUuid())).thenReturn(Optional.of(customer));
        adminService.banUser(customer.getUuid());
        ArgumentCaptor<Customer> updated = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(updated.capture());

        Customer banned  = updated.getValue();

        assertFalse(banned.isActive());
    }

    @Test
    public void deleter_success(){
        adminService.deleter(customer.getUuid());
        verify(customerRepository).deleteById(customer.getUuid());
    }
    @Test
    public void retrieveAll_success() {
        List<Customer> customers = List.of(customer);
        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = adminService.retrieve();

        assertEquals(1, result.size());
        assertEquals(customer, result.get(0));
        verify(customerRepository).findAll();
    }
    @Test
    public void retrieveById_success() {
        when(customerRepository.findById(customer.getUuid())).thenReturn(Optional.of(customer));

        Customer result = adminService.retrieve(customer.getUuid());

        assertEquals(customer, result);
        verify(customerRepository).findById(customer.getUuid());
    }
    @Test
    public void saver_success() {
        adminService.saver(customer);

        verify(customerRepository).save(customer);
    }
}
