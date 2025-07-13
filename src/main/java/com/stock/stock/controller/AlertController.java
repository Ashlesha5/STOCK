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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.stock.stock.entity.Alert;
import com.stock.stock.exception.AlertNotFoundException;
import com.stock.stock.exception.StockScripNotFoundException;
import com.stock.stock.service.AlertService;

@RestController
@RequestMapping("alertapi")
public class AlertController {

    @Autowired
    AlertService alertService;

    @PostMapping("alert")
    public ResponseEntity<String> saveAlert(@RequestBody Alert alert)
    {
        return alertService.saveAlert(alert);
    }

    @PutMapping("alert")
    public ResponseEntity<String> updateAlert(@RequestBody Alert alert)
    {
        return alertService.updateAlert(alert);
    }

    @DeleteMapping("alert/{id}")
    public ResponseEntity<String> deletealert(@PathVariable Long id)
    {
        return alertService.deletealert(id);
    }

    @GetMapping("alert/{id}")
    public ResponseEntity<Alert> fetchAlert(@PathVariable Long id)
    {
        return alertService.fetchAlert(id);
    }

    @GetMapping("alert")
    public ResponseEntity<List<Alert>> fetchAllAlert(){
        return alertService.fetchAllAlert();
    }

     @ExceptionHandler(value = StockScripNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleStockScripNotFoundException(StockScripNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

     @ExceptionHandler(value = AlertNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleAlertNotFoundException(AlertNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
