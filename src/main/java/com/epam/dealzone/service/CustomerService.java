package com.epam.dealzone.service;

import com.epam.dealzone.service.api.Deleter;
import com.epam.dealzone.service.api.Retriever;
import com.epam.dealzone.service.api.Saver;
import com.epam.dealzone.service.api.Updater;
import com.epam.dealzone.web.dto.CustomerRequest;
import com.epam.dealzone.web.dto.CustomerResponse;
import com.epam.dealzone.web.dto.ProductRequest;
import com.epam.dealzone.web.dto.ProductResponse;

import java.util.UUID;

public interface CustomerService extends Saver<CustomerRequest>,
    Retriever<CustomerResponse, UUID>, Updater<ProductRequest,UUID>, Deleter<UUID> {
}
