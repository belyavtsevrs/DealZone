package com.epam.dealzone.web.error;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    public enum CODE{
        CUSTOMER_NOT_FOUNT("");

        final String description;

        CODE(String description) {
            this.description = description;
        }
    }
}
