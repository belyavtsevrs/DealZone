package com.epam.dealzone.service;

import com.epam.dealzone.domain.entity.Customer;
import com.epam.dealzone.domain.entity.Image;
import com.epam.dealzone.domain.entity.Product;
import com.epam.dealzone.repository.CustomerRepository;
import com.epam.dealzone.repository.ProductRepository;
import com.epam.dealzone.service.impl.FileStorageServiceImpl;
import com.epam.dealzone.service.impl.ProductServiceImpl;
import com.epam.dealzone.web.dto.CustomerRequest;
import com.epam.dealzone.web.dto.ProductRequest;
import com.epam.dealzone.web.dto.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.parameters.P;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private FileStorageServiceImpl storageService;

    @InjectMocks
    private ProductServiceImpl productService;


    private ProductRequest request;
    private Product product;
    private Customer customer;
    private UUID uuid;
    private Image image;

    @BeforeEach
    void init() {
        request = new ProductRequest();
        request.setPrincipalName("qweqwewq@gmail.com");
        request.setImages(List.of(new MockMultipartFile("image.jpg","image.jpg","image","data".getBytes())));
        request.setTitle("cat");
        request.setDescription("for free");
        request.setPrice(BigDecimal.ZERO);

        uuid = UUID.randomUUID();
        product = request.toProduct();
        product.setUuid(uuid);
        product.setCreationDate(LocalDateTime.now());

        image = Image.builder()
                .url("image.jpg")
                .product(product)
                .build();
        product.setImages(List.of(image));

        customer = Customer.builder()
                .uuid(uuid)
                .email("qweqwewq@gmail.com")
                .build();
        product.setCustomer(customer);

    }

    @Test
    void testCreateWithImage_success() throws Exception {
        MultipartFile image = mock(MultipartFile.class);
        when(storageService.saveFile(eq(image), anyString())).thenReturn("image.jpg");
        when(customerRepository.findCustomerByEmail(request.getPrincipalName()))
                .thenReturn(Optional.of(customer));

        assertDoesNotThrow(()->
                productService.createWithImage(request,List.of(image)));

        verify(customerRepository).findCustomerByEmail(request.getPrincipalName());
        verify(storageService).saveFile(image,"productImages");
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void testDeleter_success() throws Exception{
        when(productRepository.findById(uuid)).thenReturn(Optional.of(product));

        assertDoesNotThrow(() -> productService.deleter(uuid));

        verify(productRepository).findById(uuid);
        verify(productRepository).deleteById(uuid);
    }
    @Test
    void testDeleter_unsuccess() throws Exception{
        assertThrows(RuntimeException.class,()->
                productService.deleter(uuid));

        verify(productRepository).findById(uuid);
        verify(productRepository, never()).deleteById(any());
    }

    @Test
    public void testRetrieveAll_success(){
        when(productRepository.findAll())
                .thenReturn(List.of(product));

        List<ProductResponse> responses = productService.retrieve();
        assertEquals(1,responses.size());
        verify(productRepository).findAll();
    }

    @Test
    public void testRetrievePagenable_success(){
        Page<Product> page = new PageImpl<>(List.of(product));
        Pageable pageable = PageRequest.of(0, 5);
        when(productRepository.findAll(pageable)).thenReturn(page);

        Page<ProductResponse> result = productService.retrieve(null, pageable);

        assertEquals(1, result.getContent().size());
        verify(productRepository).findAll(pageable);
    }
    @Test
    public void retrieveById_success(){
        when(productRepository.findById(uuid))
                .thenReturn(Optional.of(product));
        assertDoesNotThrow(()->
                productService.retrieve(uuid));
        ProductResponse response = productService.retrieve(uuid);
        assertEquals(response.getTitle(),product.getTitle());
    }

    @Test
    public void testSaver_success(){
        MultipartFile image = request.getImages().get(0);
        when(storageService.saveFile(eq(image), anyString())).thenReturn("image.jpg");
        when(customerRepository.findCustomerByEmail(request.getPrincipalName()))
                .thenReturn(Optional.of(customer));

        assertDoesNotThrow(()->
                productService.saver(request)
        );

        verify(storageService,times(1)).saveFile(image, "productImages");
        verify(productRepository).save(any(Product.class));
    }
    @Test
    public void updater_success(){
        when(customerRepository.findCustomerByEmail(request.getPrincipalName()))
                .thenReturn(Optional.of(customer));

        when(storageService.saveFile(eq(request.getImages().get(0)), anyString()))
                .thenReturn("newAvatar.jpg");

        productService.createWithImage(request, request.getImages());

        ArgumentCaptor<Product> update = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(update.capture());

        Product saved = update.getValue();

        assertEquals(request.getDescription(), saved.getDescription());
        assertEquals(customer, saved.getCustomer());
        assertFalse(saved.getImages().isEmpty());
        assertEquals("newAvatar.jpg", saved.getImages().get(0).getUrl());
    }
}

