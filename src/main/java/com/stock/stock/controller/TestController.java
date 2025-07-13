package com.stock.stock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.stock.stock.entity.ObjectTest;
import com.stock.stock.enums.Type;
import com.stock.stock.service.TestService;



@RestController
@RequestMapping("testapi")
public class TestController {

    @Autowired
    TestService testService;

    @PostMapping("test")
    public  String hello(){
        return "Hello World";
    }

    @PostMapping("test/{text}")
    public  String returnPathString(@PathVariable String text){
        return text;
    }

    @PostMapping("objectTest")
    public ObjectTest postMethodName(@RequestBody ObjectTest test) { 
        return test;
    }

    @PostMapping("object")
    public String saveObject(@RequestBody ObjectTest test ){
        return testService.saveObject(test);
    }

    @GetMapping("object")
    public List<ObjectTest> getObject()
    {
       return testService.getObject();
    }

    @DeleteMapping("object/{id}")
    public String deleteObject(@PathVariable Long id)
    {
        return testService.deleteObject(id);
    }

    @GetMapping("objects")
    public List<ObjectTest> getObjectsBasedOnParams(@RequestParam(required = false) String name, @RequestParam(required = false) Type type)
    {
        return testService.getObjectsBasedOnParams(name, type);
    }
}
