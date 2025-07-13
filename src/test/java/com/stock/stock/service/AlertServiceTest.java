package com.stock.stock.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.stock.stock.dao.AlertDao;
import com.stock.stock.dao.StockDao;
import com.stock.stock.entity.Alert;
import com.stock.stock.entity.Stock;

@ExtendWith(MockitoExtension.class)
public class AlertServiceTest {

    @Mock
    private AlertDao alertDao;

    @Mock
    private StockDao stockDao;

    @InjectMocks
    private AlertService alertService;

    @Test
    public void alertService_SaveAlert_ReturnsString()
    {
        Stock stock = new Stock();
        stock.setId(3L);
        stock.setName("HDFC Bank");
        stock.setScrip("HDFCBANK");
        stock.setPrice(1500.75);
        stock.setFlag(true);
        stock.setDescription("Large-cap IT stock");

        Alert alert = new Alert();
        alert.setActive(true);
        alert.setId(1L);
        alert.setTrigger(15.0);
        alert.setStock(stock);

        when(alertDao.save(any(Alert.class))).thenReturn(alert);
        when(stockDao.findByScrip(any(String.class))).thenReturn(Optional.of(stock));
        ResponseEntity<String> response = alertService.saveAlert(alert);
        verify (stockDao, times(1)).findByScrip(any(String.class));
        verify (alertDao, times(1)).save(any(Alert.class));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Saved", response.getBody());
    }

    



}
