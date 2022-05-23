package com.microservices.userservice.application.service.abstraction;

import com.microservices.userservice.feign.model.CarModel;

import java.util.List;

public interface IFeingCarService {
    CarModel saveCar(long userid, CarModel carModel);

    List<CarModel> getListCar(long userid);

}
