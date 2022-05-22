package com.microservices.carservice.application.service.abstraction;

import com.microservices.carservice.infrastructure.database.entity.CarEntity;

import java.util.List;

public interface ICrudCarService {
    CarEntity save(CarEntity car);

    CarEntity getCar(Long id);

    List<CarEntity> getListCars();
}
