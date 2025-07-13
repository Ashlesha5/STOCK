package com.stock.stock.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;


import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.stock.stock.entity.Stock;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class StockDaoTest {

    @Autowired
    private StockDao stockDao;

    @Test
    public void StockDao_findByScrip_ReturnStock()
    {
        Stock stock =new Stock();
        stock.setScrip("INFY");
        stock.setPrice(23);
        stock.setName("INFOSYS");
        stock.setFlag(true);
        stock.setDescription("Service based MNC");

        stockDao.save(stock);
        Optional<Stock> savedStock = stockDao.findByScrip("INFY");
        assertTrue(savedStock.isPresent());
        assertEquals("INFY", savedStock.get().getScrip());
    }

    @Test
    public void StockDao_findByFlag_ReturnStocks()
    {
        Stock stock1 =new Stock();
        stock1.setScrip("INFY");
        stock1.setPrice(23);
        stock1.setName("INFOSYS");
        stock1.setFlag(true);
        stock1.setDescription("Service based MNC");

        Stock stock2 =new Stock();
        stock2.setScrip("TCS");
        stock2.setPrice(23);
        stock2.setName("TCS");
        stock2.setFlag(true);
        stock2.setDescription("Service based MNC");
        
        stockDao.save(stock2);
        stockDao.save(stock1);
        List<Stock> savedStock =stockDao.findByFlag(true);
        assertEquals(2, savedStock.size());
        assertThat(savedStock)
                 .extracting(Stock::getScrip)
                .containsExactlyInAnyOrder("INFY", "TCS");
    }

}
