package com.microservices.carservice.infrastructure.database.repository;

import com.microservices.carservice.infrastructure.database.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<CarEntity,Long> {

    List<CarEntity> findByUserId(long userId);
}
