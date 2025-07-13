package com.stock.stock.dao;

import com.stock.stock.entity.ObjectTest;
import com.stock.stock.enums.Type;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestDao extends JpaRepository<ObjectTest, Long>{

    List<ObjectTest> findByNameAndType(String name, Type type);

    List<ObjectTest> findByName(String name);

    List<ObjectTest> findByType(Type type);

}
