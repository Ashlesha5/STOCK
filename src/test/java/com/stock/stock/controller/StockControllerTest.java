package com.stock.stock.controller;

import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.stock.entity.Stock;
import com.stock.stock.service.StockService;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = StockController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class StockControllerTest {

    @MockBean
private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockService stockService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void stockController_saveStock_ReturnString() throws Exception
    {
Stock stock = new Stock();
        stock.setId(1L);
        stock.setName("HDFC Bank");
        stock.setScrip("HDFCBANK");
        stock.setPrice(1500.75);
        stock.setFlag(true);
        stock.setDescription("Large-cap stock");

        // Mock service behavior
        Mockito.when(stockService.saveStock(any(Stock.class)))
               .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body("Success"));

        // Perform POST request
        mockMvc.perform(post("/stockapi/stock")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stock)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Success"));
    }

 @Test
    void testSaveStock_WithEmptyJson_ShouldReturnBadRequest() throws Exception {
        String emptyJson = "{}";

        mockMvc.perform(post("/stockapi/stock")
                .contentType(MediaType.APPLICATION_JSON)
                .content(emptyJson))
            .andExpect(status().isBadRequest());
    }


}
