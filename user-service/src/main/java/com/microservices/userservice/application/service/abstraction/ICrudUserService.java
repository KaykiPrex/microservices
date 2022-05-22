package com.microservices.userservice.application.service.abstraction;

import com.microservices.userservice.infrastructure.database.entity.UserEntity;

import java.util.List;

public interface ICrudUserService {
    UserEntity save(UserEntity car);

    UserEntity getUser(Long id);

    List<UserEntity> getListUsers();
}
