package com.epam.dealzone.service.api;

import java.util.List;

public interface Retriever<E,ID> {
    List<E> retrieve();
    List<E> retrieve(int page,int size);
    E retrieve(ID id);
}
