//package com.example.redis.controller;
//
//import com.example.redis.entity.Student;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.web.bind.annotation.*;
//
///**
// * @author Lucas (Weiye) Wang
// * @version 1.0.0
// * @date 18/10/2023 - 2:42 pm
// * @Description
// */
//@RestController
//public class StudentController {
//
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    @PostMapping("/set")
//    public void set(@RequestBody Student student){
//        redisTemplate.opsForValue().set("student",student);
//    }
//
//    @GetMapping("/get/{key}")
//    public Student get(@PathVariable("key") String key){
//        return (Student) redisTemplate.opsForValue().get(key);
//    }
//
//    @DeleteMapping("/delete/{key}")
//    public boolean delete(@PathVariable("key") String key){
//        redisTemplate.delete(key);
//        return redisTemplate.hasKey(key);
//    }
//}
