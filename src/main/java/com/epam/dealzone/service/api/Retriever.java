package com.epam.dealzone.service.api;

import com.epam.dealzone.web.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface Retriever<E,ID> {
    List<E> retrieve();
    E retrieve(ID id);
    E retrieve(String name);
    Page<E> retrieve(String search, Pageable pageable);
}
