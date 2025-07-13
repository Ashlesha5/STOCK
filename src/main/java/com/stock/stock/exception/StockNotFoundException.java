package com.stock.stock.exception;

public class StockNotFoundException extends RuntimeException {

    public StockNotFoundException(Long id) {
        super("Stock with id " + id + " not found.");
    }

}
