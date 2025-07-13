package com.stock.stock.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stock.stock.entity.Stock;
import java.util.List;
import java.util.Optional;


@Repository
public interface StockDao extends JpaRepository<Stock, Long> {

    List<Stock> findByFlag(boolean flag);
    Optional<Stock> findByScrip(String scrip);

}
