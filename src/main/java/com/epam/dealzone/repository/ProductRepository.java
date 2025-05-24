package com.epam.dealzone.repository;

import com.epam.dealzone.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,String> {
}
