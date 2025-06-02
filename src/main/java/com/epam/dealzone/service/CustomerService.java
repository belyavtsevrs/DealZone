package com.epam.dealzone.service;

import com.epam.dealzone.service.api.Deleter;
import com.epam.dealzone.service.api.Retriever;
import com.epam.dealzone.service.api.Saver;
import com.epam.dealzone.service.api.Updater;
import com.epam.dealzone.web.dto.CustomerRequest;
import com.epam.dealzone.web.dto.CustomerResponse;

import java.util.UUID;

public interface CustomerService extends Saver<CustomerRequest>,
    Retriever<CustomerResponse, UUID>, Updater<CustomerRequest,UUID>, Deleter<UUID> {
}
