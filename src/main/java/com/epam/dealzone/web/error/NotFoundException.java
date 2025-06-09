package com.epam.dealzone.web.error;

import lombok.Getter;
import org.aspectj.apache.bcel.classfile.Code;

@Getter
public class NotFoundException extends RuntimeException {
    @Getter
    public enum CODE{
        CUSTOMER_NOT_FOUND_WITH_SUCH_ID("Customer with such id not found"),
        PRODUCT_NOT_FOUND_WITH_SUCH_ID("Customer with such id not found"),
        CUSTOMER_NOT_FOUND_WITH_SUCH_EMAIL("Customer with such id not found"),
        PRODUCT_NOT_FOUND_WITH_SUCH_TITLE("Customer with such id not found");

        final String description;

        CODE(String description) {
            this.description = description;
        }

        public NotFoundException get() {
            return new NotFoundException(this, this.description);
        }

        public NotFoundException get(String msg) {
            return new NotFoundException(this, this.description + " : " + msg);
        }

        public NotFoundException get(Throwable e) {
            return new NotFoundException(this, this.description + " : " + e.getMessage());
        }

        public NotFoundException get(Throwable e, String msg) {
            return new NotFoundException(this, e, this.description + " : " + msg);
        }

    }
    protected NotFoundException.CODE code;

    protected NotFoundException(NotFoundException.CODE code, Throwable e, String msg) {
        super(msg, e);
        this.code = code;
    }

    protected NotFoundException(NotFoundException.CODE code, String msg) {
        this(code, null, msg);
    }

}
