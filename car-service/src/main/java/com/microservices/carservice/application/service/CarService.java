package com.microservices.carservice.application.service;

import com.microservices.carservice.application.service.abstraction.ICrudCarService;
import com.microservices.carservice.application.service.abstraction.IGetCarService;
import com.microservices.carservice.infrastructure.database.entity.CarEntity;
import com.microservices.carservice.infrastructure.database.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService implements ICrudCarService, IGetCarService {
    @Autowired
    CarRepository carRepository;

    @Override
    public CarEntity save(CarEntity car) {
        return carRepository.save(car);
    }

    @Override
    public CarEntity getCar(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    @Override
    public List<CarEntity> getListCars() {
        return carRepository.findAll();
    }

    @Override
    public List<CarEntity> listByUserId(long id) {
        return carRepository.findByUserId(id);
    }
}
