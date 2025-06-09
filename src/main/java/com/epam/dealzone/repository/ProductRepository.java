package com.epam.dealzone.repository;

import com.epam.dealzone.domain.entity.Product;
import com.epam.dealzone.web.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Page<Product> findAllByTitleIgnoreCase(String search,Pageable pageable);
    Page<Product> findAll(Pageable pageable);

}
