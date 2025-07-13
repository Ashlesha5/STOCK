package com.stock.stock.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.stock.stock.dao.AlertDao;
import com.stock.stock.dao.StockDao;
import com.stock.stock.entity.Alert;
import com.stock.stock.entity.Stock;
import com.stock.stock.exception.AllStocksAreEmptyException;
import com.stock.stock.exception.StockNotFoundException;

@Service
public class StockService {

    @Autowired
    StockDao stockDao;

    @Autowired
    AlertDao alertDao;

    public ResponseEntity<String> saveStock(Stock stock) {
       stockDao.save(stock);
       return new ResponseEntity<>("Success",HttpStatus.CREATED);
    }

    public ResponseEntity<String> updateStock(Stock stock) {
        Optional<Stock> existingStock = stockDao.findById(stock.getId());
        if(!existingStock.isPresent())
        {
            throw new StockNotFoundException(stock.getId());
        }
        else{
            stockDao.save(stock);
            return new ResponseEntity<>("Success",HttpStatus.CREATED);
        }
     }


    public ResponseEntity<String> deleteStock(Long id) {
        Optional<Stock> existingStock = stockDao.findById(id);
        if(!existingStock.isPresent()){
            throw new StockNotFoundException(id);
        }
        else{
       Stock stock = existingStock.get();
       String stockScrip= stock.getScrip();
       List<Alert> alertScrip = alertDao.findByStockScrip(stockScrip);
       List<Long> alertIds = alertScrip.stream()
                                    .map(Alert::getId)
                                    .collect(Collectors.toList());
       alertDao.deleteAllById(alertIds);
       stockDao.deleteById(id);
       return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
    }

    public ResponseEntity<Stock> fetchStock(Long id) {
       Optional<Stock> existingStock = stockDao.findById(id);
       if(!existingStock.isPresent()){
            throw new StockNotFoundException(id);
        }
        else{
            return new ResponseEntity<>(existingStock.get(),HttpStatus.OK);
        }
    }

    public ResponseEntity<List<Stock>> fetchAllStock(Boolean flag)
    {
        if(flag != null){
            List<Stock> allStocksByFlag = stockDao.findByFlag(flag);
            if(allStocksByFlag.isEmpty()){
                throw new AllStocksAreEmptyException();
            }
            else
            return new ResponseEntity<>(allStocksByFlag, HttpStatus.OK);
        }
        else
        {
        List<Stock> allStocks = stockDao.findAll();
        if(allStocks.isEmpty()){
            throw new AllStocksAreEmptyException();
        }
        else
        return new ResponseEntity<>(allStocks, HttpStatus.OK);
        }
    }



}
