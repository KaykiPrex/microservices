package com.microservices.carservice.application.service.abstraction;

import com.microservices.carservice.infrastructure.database.entity.CarEntity;

import java.util.List;

public interface IGetCarService {
    List<CarEntity> listByUserId(long id);
}
