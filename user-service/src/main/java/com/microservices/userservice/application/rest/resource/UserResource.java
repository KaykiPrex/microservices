package com.microservices.userservice.application.rest.resource;

import com.microservices.userservice.application.service.UserService;
import com.microservices.userservice.application.service.abstraction.ICrudUserService;
import com.microservices.userservice.application.service.abstraction.IFeingCarService;
import com.microservices.userservice.feign.model.CarModel;
import com.microservices.userservice.infrastructure.database.entity.UserEntity;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("users")
public class UserResource {

    @Autowired
    private ICrudUserService crudUserService;
    @Autowired
    private IFeingCarService feingCarService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserEntity>> getList() {
        List<UserEntity> users = crudUserService.getListUsers();
        if(users.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(users);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserEntity> getById(@PathVariable("id") long id) {
        UserEntity user = crudUserService.getUser(id);
        if(user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<UserEntity> save(@RequestBody UserEntity user) {
        UserEntity userNew = crudUserService.save(user);
        return ResponseEntity.ok(userNew);
    }

    @CircuitBreaker(name = "carsCBrk", fallbackMethod = "fallBackGetListCars")
    @GetMapping("cars/{userid}")
    public ResponseEntity<List<CarModel>> getListCars(@PathVariable("userid") long userid) {
        List<CarModel> cars = feingCarService.getListCar(userid);
        if(cars.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(cars);
    }

    @PostMapping("cars/{userid}")
    public ResponseEntity<CarModel> saveCar(@PathVariable("userid") long userid, @RequestBody CarModel carModel){
        CarModel car = feingCarService.saveCar(userid,carModel);
        return ResponseEntity.ok(car);
    }

    @GetMapping("all/{userid}")
    public ResponseEntity<Map<String,Object>> getAll(@PathVariable("userid") long userid) {
        Map<String,Object> result = userService.getUserAndCar(userid);
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<List<CarModel>> fallBackGetListCars(@PathVariable("userid") long userid, RuntimeException e) {
        return new ResponseEntity("Exception : Error al hacer la peticion a car-service", HttpStatus.OK);
    }

}
