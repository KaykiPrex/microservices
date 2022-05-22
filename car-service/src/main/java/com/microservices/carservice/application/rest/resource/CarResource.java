package com.microservices.carservice.application.rest.resource;

import com.microservices.carservice.application.service.abstraction.ICrudCarService;
import com.microservices.carservice.application.service.abstraction.IGetCarService;
import com.microservices.carservice.infrastructure.database.entity.CarEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cars")
public class CarResource {

    @Autowired
    private ICrudCarService crudCarService;

    @Autowired
    private IGetCarService getCarService;

    @GetMapping
    public ResponseEntity<List<CarEntity>> getList() {
        List<CarEntity> cars = crudCarService.getListCars();
        if(cars.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("{id}")
    public ResponseEntity<CarEntity> getById(@PathVariable("id") long id) {
        CarEntity car = crudCarService.getCar(id);
        if(car == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(car);
    }

    @PostMapping
    public ResponseEntity<CarEntity> save(@RequestBody CarEntity car) {
        CarEntity carNew = crudCarService.save(car);
        return ResponseEntity.ok(carNew);
    }

    @GetMapping("users/{userid}")
    public ResponseEntity<List<CarEntity>> getListByUserId(@PathVariable("userid") long userid) {
        List<CarEntity> cars = getCarService.listByUserId(userid);
        //if(cars.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(cars);
    }


}
