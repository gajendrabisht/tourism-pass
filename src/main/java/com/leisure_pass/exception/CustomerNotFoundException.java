package com.leisure_pass.exception;

import java.util.UUID;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(UUID customerId) {
        super("Customer Not Found for id: " + customerId.toString());
    }

}
