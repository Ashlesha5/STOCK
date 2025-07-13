package com.stock.stock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.stock.dao.TestDao;
import com.stock.stock.entity.ObjectTest;
import com.stock.stock.enums.Type;

@Service
public class TestService {

    @Autowired
    TestDao testDao;

    public String saveObject(ObjectTest test){
         testDao.save(test);
         return "success";
    }

    public List<ObjectTest> getObject() {
        return testDao.findAll();
    }

    public String deleteObject(Long id) {
       testDao.deleteById(id);
       return "Object Deleted";
    }

    public List<ObjectTest> getObjectsBasedOnParams(String name, Type type){
        if(name!= null && type!= null)
            return testDao.findByNameAndType(name, type);
        else if(name!=null)
            return testDao.findByName(name);
        else if(type!=null)
            return testDao.findByType(type);
        else
            return testDao.findAll();
    }

}
