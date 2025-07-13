package com.stock.stock.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stock.stock.entity.Alert;
import java.util.List;
import java.util.Optional;


@Repository
public interface AlertDao extends JpaRepository<Alert, Long> {

    Optional<List<Alert>> findByActive(Boolean active);
    List<Alert> findByStockScrip(String scrip);
}
