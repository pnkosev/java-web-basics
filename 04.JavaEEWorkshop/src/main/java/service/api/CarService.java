package service.api;

import domain.model.service.CarServiceModel;

import java.util.List;

public interface CarService {
    void createCar(CarServiceModel car);

    List<CarServiceModel> getAllCars();
}
