package com.epam.dealzone.service;

import com.epam.dealzone.domain.entity.Customer;
import com.epam.dealzone.service.api.*;

import java.util.UUID;

public interface AdminService extends
        Deleter<UUID>,
        Retriever<Customer,UUID>,
        Updater<Customer,UUID>,
        Saver<Customer>,
        BannerUser<UUID>
{

}
