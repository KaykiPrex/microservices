package com.microservices.userservice.feign.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CarListModel {
    private List<CarModel> carModels;

    public CarListModel() {
        carModels = new ArrayList<>();
    }
}
