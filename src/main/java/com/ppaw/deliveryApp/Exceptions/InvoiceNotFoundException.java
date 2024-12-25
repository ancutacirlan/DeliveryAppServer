package com.ppaw.deliveryApp.Exceptions;

public class InvoiceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvoiceNotFoundException(String customMessage) {
        super(customMessage);
    }
}
