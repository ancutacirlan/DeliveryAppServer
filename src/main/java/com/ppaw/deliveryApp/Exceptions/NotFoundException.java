package com.ppaw.deliveryApp.Exceptions;

public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotFoundException(String customMessage) {
        super(customMessage);
    }
}
