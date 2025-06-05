package com.epam.dealzone.service.api;

import com.epam.dealzone.web.dto.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface Paginator<E> {
    List<E> get(String search,int page,int size);
}
