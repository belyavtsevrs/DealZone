package com.epam.dealzone.service.api;

public interface Favoriter<CustomerId,ProductId> {
    void addFavorite(CustomerId cId,ProductId pId);
}
