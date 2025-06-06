package com.epam.dealzone.service;

import com.epam.dealzone.service.api.*;
import com.epam.dealzone.web.dto.CustomerRequest;
import com.epam.dealzone.web.dto.CustomerResponse;

import java.util.UUID;

public interface CustomerService extends Saver<CustomerRequest>,
    Retriever<CustomerResponse, UUID>,
        Updater<CustomerRequest,UUID>,
        Deleter<UUID>,
        Favoriter<UUID,UUID> {
}
