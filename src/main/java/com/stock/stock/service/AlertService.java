package com.stock.stock.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.stock.stock.dao.AlertDao;
import com.stock.stock.dao.StockDao;
import com.stock.stock.entity.Alert;
import com.stock.stock.entity.Stock;
import com.stock.stock.exception.AlertNotFoundException;
import com.stock.stock.exception.StockScripNotFoundException;

@Service
public class AlertService {

    @Autowired
    AlertDao alertDao;

    @Autowired
    StockDao stockDao;

    public ResponseEntity<String> saveAlert(Alert alert)
    {
         Stock existingStock = stockDao.findByScrip(alert.getStock().getScrip()) 
                .orElseThrow(() -> new StockScripNotFoundException());

        // Set the managed Stock to Alert
        alert.setStock(existingStock);
        alertDao.save(alert);
        return new ResponseEntity<>("Saved", HttpStatus.OK);
    }

     public ResponseEntity<String> updateAlert(Alert alert) {
        Optional<Alert> existingAlert = alertDao.findById(alert.getId());
        if(existingAlert.isPresent())
        {
            //Get the scrip from your alert object.
            String scrip=alert.getStock().getScrip();
            // Search the database to find the Stock with that scrip.
            //Optional<Stock> managedStock = stockDao.findByScrip(scrip);
            //Once you find that Stock, get its id.
            //Stock stock = managedStock.get();
             Stock stock = stockDao.findByScrip(scrip)
                   .orElseThrow(() -> new StockScripNotFoundException());
            //Long id = stock.getId();
            alert.setStock(stock);
            alertDao.save(alert);
            return new ResponseEntity<>("Updated",HttpStatus.OK);
        }
        else
         throw new AlertNotFoundException(alert.getId());
    }

    public ResponseEntity<String> deletealert(Long id)
    {
         Optional<Alert> optionalAlert = alertDao.findById(id);
        if(optionalAlert.isPresent()){
        Alert managedAlert = optionalAlert.get();
        managedAlert.setActive(false);
        alertDao.save(managedAlert);
          return new ResponseEntity<>("Alert set false",HttpStatus.OK);
        }
        else
          throw new AlertNotFoundException(id);
    }

    public ResponseEntity<Alert> fetchAlert(Long id) {
        Optional<Alert> optionalAlert= alertDao.findById(id);
        if(optionalAlert.isPresent()){
        Alert managedAlert = optionalAlert.get();
        return new ResponseEntity<>(managedAlert,HttpStatus.OK);
        }
        else
          throw new AlertNotFoundException(id);
    }

    public ResponseEntity<List<Alert>> fetchAllAlert() {
        Optional<List<Alert>> optionalAlert = alertDao.findByActive(true);
        List<Alert> managedAlert = optionalAlert.get();
        return new ResponseEntity<>(managedAlert,HttpStatus.OK);
    }
}