package com.microservices.userservice.application.service;

import com.microservices.userservice.application.service.abstraction.ICrudUserService;
import com.microservices.userservice.application.service.abstraction.IFeingCarService;
import com.microservices.userservice.feign.CarFeignClient;
import com.microservices.userservice.feign.model.CarListModel;
import com.microservices.userservice.feign.model.CarModel;
import com.microservices.userservice.infrastructure.database.entity.UserEntity;
import com.microservices.userservice.infrastructure.database.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserService implements ICrudUserService, IFeingCarService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CarFeignClient carFeignClient;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public UserEntity getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<UserEntity> getListUsers() {
        return userRepository.findAll();
    }



    @Override
    public CarModel saveCar(long userid, CarModel carModel){
        log.info("ID > "+userid);
        carModel.setUserId(userid);
        return carFeignClient.save(carModel);
    }

    @Override
    public List<CarModel> getListCar(long userid) {
        log.info("CAR-SERVICE> "+userid);
        List<CarModel> carsList = restTemplate.getForObject("http://car-service/cars/users/{id}",List.class,userid);
        log.info("REST TEMPLATE> ");
        return carsList;
    }

    public Map<String, Object> getUserAndCar(long userId) {
        Map<String, Object> result = new LinkedHashMap<>();
        UserEntity user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            result.put("Message", "usuario inexistente");
            return  result;
        }
        result.put("User",user);
        List<CarModel> cars = carFeignClient.getListByUserId(userId);
        if (cars.isEmpty()) result.put("Cars","usuario no tiene autos");
        else result.put("Cars",cars);

        return result;
    }
}
