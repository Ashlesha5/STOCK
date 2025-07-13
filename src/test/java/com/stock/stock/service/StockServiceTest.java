package com.stock.stock.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.stock.stock.dao.AlertDao;
import com.stock.stock.dao.StockDao;
import com.stock.stock.entity.Alert;
import com.stock.stock.entity.Stock;
import com.stock.stock.exception.StockNotFoundException;
import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class StockServiceTest {

    @Mock
    private StockDao stockDao;

    @Mock
    private AlertDao alertDao;

    @InjectMocks
    private StockService stockService;


    @Test
    public void saveStockTest(){
        Stock stock = new Stock();
        stock.setId(3L);
        stock.setName("HDFC Bank");
        stock.setScrip("HDFCBANK");
        stock.setPrice(1500.75);
        stock.setFlag(true);
        stock.setDescription("Large-cap IT stock");

          //doNothing().when(stockDao).save(stock);
        when(stockDao.save(any(Stock.class))).thenReturn(stock);
        ResponseEntity<String> response = stockService.saveStock(stock);
        //.out.println("Hi Test now");
        // System.out.println(">>> Response Status: " + response.getStatusCode());
       // System.out.println(">>> Response Body: " + response.getBody());
 
        verify (stockDao, times(1)).save(any(Stock.class));
        // verify(stockDao).save(any(Stock.class));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Success", response.getBody());
       
   
    }

    @Test
    public void  updateStockTest()
    {
          Stock stock = new Stock();
        stock.setId(1L);
        stock.setName("Infosys");
        stock.setScrip("INFY");
        stock.setPrice(1500);
        stock.setFlag(true);
        stock.setDescription("Large-cap IT stock");

        when(stockDao.findById(any(Long.class))).thenReturn(Optional.of(stock));
        when(stockDao.save(any(Stock.class))).thenReturn(stock);

        ResponseEntity<String> response = stockService.updateStock(stock);
        verify (stockDao, times(1)).findById(any(Long.class));
        verify (stockDao, times(1)).save(any(Stock.class));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Success", response.getBody());

    }

    @Test
public void updateStock_WhenStockDoesNotExist_ShouldThrowException() {
    Stock stock = new Stock();
    stock.setId(99L);
    stock.setScrip("XYZ");

    when(stockDao.findById(99L)).thenReturn(Optional.empty());

    StockNotFoundException ex = assertThrows(
        StockNotFoundException.class,
        () -> stockService.updateStock(stock)
    );

    assertEquals("Stock with id 99 not found.", ex.getMessage());
    verify(stockDao, times(1)).findById(99L);
    verify(stockDao, never()).save(any());
}

@Test
public void stockService_Delete_ReturnsStringMsg(){
    Stock stock = new Stock();
    stock.setId(1L);
        stock.setName("Infosys");
        stock.setScrip("INFY");
        stock.setPrice(1500);
        stock.setFlag(true);
        stock.setDescription("Large-cap IT stock");

    Alert alert = new Alert();
    alert.setActive(true);
    alert.setStock(stock);
    alert.setTrigger(14.0);
    

    when(stockDao.findById(any(Long.class))).thenReturn(Optional.of(stock));
    when(alertDao.findByStockScrip(any(String.class))).thenReturn(List.of(alert));
    doNothing().when(alertDao).deleteAllById(any(List.class));
    doNothing().when(stockDao).deleteById(any(Long.class));
    
    ResponseEntity<String> response = stockService.deleteStock(stock.getId());
    
    verify (stockDao, times(1)).findById(any(Long.class));
    verify (alertDao, times(1)).deleteAllById(any(List.class));
    verify (alertDao, times(1)).findByStockScrip(any(String.class));
    verify (stockDao, times(1)).deleteById(any(Long.class));

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Deleted", response.getBody());

}

@Test
public void stockService_FetchById_ReturnStock()
{
     Stock stock = new Stock();
    stock.setId(1L);
        stock.setName("Infosys");
        stock.setScrip("INFY");
        stock.setPrice(1500);
        stock.setFlag(true);
        stock.setDescription("Large-cap IT stock");

    when(stockDao.findById(any(Long.class))).thenReturn(Optional.of(stock));

    ResponseEntity<Stock> response = stockService.fetchStock(stock.getId());
    verify (stockDao, times(1)).findById(any(Long.class));
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(stock.getId(), response.getBody().getId());

}

@Test
public void stockService_FetchAllStock_ReturnStocks()
{
    Stock stock = new Stock();
    stock.setId(1L);
        stock.setName("Infosys");
        stock.setScrip("INFY");
        stock.setPrice(1500);
        stock.setFlag(true);
        stock.setDescription("Large-cap IT stock");

     when(stockDao.findByFlag(any(boolean.class))).thenReturn(List.of(stock));
    // when(stockDao.findAll()).thenReturn(List.of(stock));

     ResponseEntity<List<Stock>> response = stockService.fetchAllStock(stock.isFlag());
     verify (stockDao, times(1)).findByFlag(any(boolean.class));
     verify (stockDao, times(0)).findAll();

     assertEquals(HttpStatus.OK, response.getStatusCode());
     List<Stock> listStock = response.getBody();
    assertThat(listStock)
    .extracting(Stock::getId)
    .containsExactlyInAnyOrder(stock.getId());
}




}
