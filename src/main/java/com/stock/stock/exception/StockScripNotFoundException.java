package com.stock.stock.exception;

public class StockScripNotFoundException extends RuntimeException {
 
    public StockScripNotFoundException() {
        super("Stock scrip is not available in the system.");
    }
}
