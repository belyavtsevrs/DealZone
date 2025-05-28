package com.epam.dealzone.service.api;

import java.util.List;

public interface Retriever<E,ID> {
    List<E> retrieve();
    E retrieve(ID id);
}
