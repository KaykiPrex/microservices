package com.microservices.userservice.application.rest.resource;

import com.microservices.userservice.application.rest.resource.abstraction.IUserOperations;
import com.microservices.userservice.application.service.UserService;
import com.microservices.userservice.application.service.abstraction.ICrudUserService;
import com.microservices.userservice.application.service.abstraction.IFeingCarService;
import com.microservices.userservice.feign.model.CarModel;
import com.microservices.userservice.infrastructure.database.entity.UserEntity;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("users")
public class UserResource implements IUserOperations {

    @Autowired
    private ICrudUserService crudUserService;
    @Autowired
    private IFeingCarService feingCarService;

    @Autowired
    private UserService userService;

    static final String MESSAGE_CAR_SERVICE_UNAVAILABLE = "Message: No servers available for service: car-service";

    @Override
    public ResponseEntity<List<UserEntity>> getList() {
        List<UserEntity> users = crudUserService.getListUsers();
        if(users.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<UserEntity> getById(@PathVariable("id") long id) {
        UserEntity user = crudUserService.getUser(id);
        if(user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<UserEntity> save(@RequestBody UserEntity user) {
        UserEntity userNew = crudUserService.save(user);
        return ResponseEntity.ok(userNew);
    }

    @Override
    public ResponseEntity<Map<String,Object>> getAll(@PathVariable("userid") long userid) {
        Map<String,Object> result = userService.getUserAndCar(userid);
        return ResponseEntity.ok(result);
    }

    @Override
    @CircuitBreaker(name = "carsCBrk", fallbackMethod = "fallBackSaveCars")
    public ResponseEntity<CarModel> saveCar(@PathVariable("userid") long userid, @RequestBody CarModel carModel){
        CarModel car = feingCarService.saveCar(userid,carModel);
        if(car == null ) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(car);
    }

    @Override
    @CircuitBreaker(name = "carsCBrk", fallbackMethod = "fallBackGetListCars")
    public ResponseEntity<List<CarModel>> getListCars(@PathVariable("userid") long userid) {
        List<CarModel> cars = feingCarService.getListCar(userid);
        if(cars.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(cars);
    }

    public ResponseEntity<List<CarModel>> fallBackGetListCars(@PathVariable("userid") long userid, RuntimeException e) {
        return new ResponseEntity(MESSAGE_CAR_SERVICE_UNAVAILABLE, HttpStatus.SERVICE_UNAVAILABLE);
    }

    public ResponseEntity<CarModel> fallBackSaveCars(@PathVariable("userid") long userid, @RequestBody CarModel carModel, RuntimeException e) {
        return new ResponseEntity(MESSAGE_CAR_SERVICE_UNAVAILABLE, HttpStatus.SERVICE_UNAVAILABLE);
    }

}
