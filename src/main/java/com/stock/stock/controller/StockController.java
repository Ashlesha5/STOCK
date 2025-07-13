package com.stock.stock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.stock.stock.entity.Stock;
import com.stock.stock.exception.AllStocksAreEmptyException;
import com.stock.stock.exception.StockNotFoundException;
import com.stock.stock.service.StockService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("stockapi")
public class StockController {

    @Autowired
    StockService stockService;

    @PostMapping("stock")
    public ResponseEntity<String> saveStock(@Valid @RequestBody Stock stock)
    {
        return stockService.saveStock(stock);
    }

    @PutMapping("stock")
    public ResponseEntity<String> updateStock(@RequestBody Stock stock)
    {
        return stockService.updateStock(stock);
    }

    @DeleteMapping("stock/{id}")
    public ResponseEntity<String> deleteStock(@PathVariable Long id)
    {
        return stockService.deleteStock(id);
    }

    @GetMapping("stock/{id}")
    public ResponseEntity<Stock> fetchStock(@PathVariable Long id)
    {
        return stockService.fetchStock(id);
    } 

    @GetMapping("stocks")
    public ResponseEntity<List<Stock>> fetchAllStock(@RequestParam(required=false) Boolean flag)
    {
        return stockService.fetchAllStock(flag); 
    }

    @ExceptionHandler(value = StockNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleStockNotFoundException(StockNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = AllStocksAreEmptyException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleAllStocksAreEmptyException(AllStocksAreEmptyException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
