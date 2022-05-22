package com.microservices.userservice.feign;

import com.microservices.userservice.feign.model.CarModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "car-service", path = "cars")
public interface CarFeignClient {

    @PostMapping
    public CarModel save(@RequestBody CarModel car);

    @GetMapping("users/{userid}")
    public List<CarModel> getListByUserId(@PathVariable("userid") long userid);

}
