package com.epam.dealzone.service.api;

public interface Updater<E,ID> {
    void updater(E e,ID id);
}
