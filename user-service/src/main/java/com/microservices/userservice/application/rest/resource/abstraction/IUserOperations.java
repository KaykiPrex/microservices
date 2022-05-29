package com.microservices.userservice.application.rest.resource.abstraction;

import com.microservices.userservice.feign.model.CarModel;
import com.microservices.userservice.infrastructure.database.entity.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/default")
public interface IUserOperations {

    @GetMapping
    ResponseEntity<List<UserEntity>> getList();

    @GetMapping("{id}")
    ResponseEntity<UserEntity> getById(@PathVariable("id") long id);

    @PostMapping
    ResponseEntity<UserEntity> save(@RequestBody UserEntity user);

    @GetMapping("{userid}/cars")
    ResponseEntity<List<CarModel>> getListCars(@PathVariable("userid") long userid);

    @PostMapping("{userid}/cars")
    ResponseEntity<CarModel> saveCar(@PathVariable("userid") long userid, @RequestBody CarModel carModel);

    @GetMapping("{userid}/all")
    ResponseEntity<Map<String,Object>> getAll(@PathVariable("userid") long userid);
}
