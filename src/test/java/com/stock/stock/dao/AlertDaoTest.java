package com.stock.stock.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

import com.stock.stock.entity.Alert;
import com.stock.stock.entity.Stock;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AlertDaoTest {

    @Autowired
    AlertDao alertDao;

    @Autowired
    StockDao stockDao;

    private Stock stock1;
    private Stock stock2;

    @BeforeEach
    void setup()
    {
         stock1 =new Stock();
        stock1.setScrip("INFY");
        stock1.setPrice(23);
        stock1.setName("INFOSYS");
        stock1.setFlag(true);
        stock1.setDescription("Service based MNC");

         stock2 =new Stock();
        stock2.setScrip("TCS");
        stock2.setPrice(23);
        stock2.setName("ICS");
        stock2.setFlag(true);
        stock2.setDescription("Service based MNC");

        stockDao.save(stock1);
        stockDao.save(stock2);
    }
    


@Test
public void AlertDao_findByActive_ReturnAlerts()
{
  Alert alert1 = new Alert();
  alert1.setActive(false);
  alert1.setTrigger(15);
  alert1.setUpdatedAt(LocalDateTime.now());
  alert1.setStock(stock1);
  alertDao.save(alert1);

  Alert alert2 = new Alert();
  alert2.setActive(true);
  alert2.setTrigger(15);
  alert2.setUpdatedAt(LocalDateTime.now());
  alert2.setStock(stock2);
  alertDao.save(alert2);

  Optional<List<Alert>> listAlert= alertDao.findByActive(true);
  assertTrue(listAlert.isPresent());
  assertEquals(alert2.getId(), listAlert.get().get(0).getId());


}

@Test
public void AlertDao_findByStockScrip_ReturnAlerts()
{
  Alert alert1 = new Alert();
  alert1.setActive(false);
  alert1.setTrigger(15.0);
  alert1.setUpdatedAt(LocalDateTime.now());
  alert1.setStock(stock1);
  alertDao.save(alert1);

  Alert alert2 = new Alert();
  alert2.setActive(false);
  alert2.setTrigger(16.0);
  alert2.setUpdatedAt(LocalDateTime.now());
  alert2.setStock(stock1);
  alertDao.save(alert2);

  List<Alert> listAlerts = alertDao.findByStockScrip("INFY");
  assertEquals(2, listAlerts.size());
  assertThat(listAlerts)
    .extracting(Alert::getTrigger)
    .containsExactlyInAnyOrder(16.0,15.0);


}


}
