package com.microservices.userservice.application.service.abstraction;

import com.microservices.userservice.feign.model.CarModel;

public interface IFeingCarService {
    public CarModel saveCar(long id, CarModel carModel);
}
