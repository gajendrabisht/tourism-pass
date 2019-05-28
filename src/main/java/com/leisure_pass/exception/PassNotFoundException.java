package com.leisure_pass.exception;

import java.util.UUID;

public class PassNotFoundException extends RuntimeException {

    public PassNotFoundException(UUID passId) {
        super("Pass Not Found for id: " + passId.toString());
    }

}
