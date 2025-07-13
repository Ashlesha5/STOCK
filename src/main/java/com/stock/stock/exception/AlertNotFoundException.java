package com.stock.stock.exception;

public class AlertNotFoundException extends RuntimeException {
     public AlertNotFoundException(Long id) {
        super("Alert with id " + id + " not found.");
    }
}
