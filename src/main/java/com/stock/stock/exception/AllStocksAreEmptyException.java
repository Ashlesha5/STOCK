package com.stock.stock.exception;

public class AllStocksAreEmptyException extends RuntimeException {
 
    public AllStocksAreEmptyException() {
        super("No stocks available in the system.");
    }
}
