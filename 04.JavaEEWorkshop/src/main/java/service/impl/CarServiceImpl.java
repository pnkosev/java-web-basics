package service.impl;

import domain.entity.Car;
import domain.model.service.CarServiceModel;
import domain.model.service.UserServiceModel;
import org.modelmapper.ModelMapper;
import repository.api.CarRepository;
import service.api.CarService;
import service.api.UserService;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class CarServiceImpl implements CarService {

    private final UserService userService;

    private final CarRepository carRepository;

    private final ModelMapper modelMapper;

    @Inject
    public CarServiceImpl(UserService userService, CarRepository carRepository, ModelMapper modelMapper) {
        this.userService = userService;
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createCar(CarServiceModel car) {
        UserServiceModel user = this.userService
                .findUserByUsername(car.getUser().getUsername());

        car.setUser(user);

        this.carRepository
                .merge(this.modelMapper.map(car, Car.class));
    }

    @Override
    public List<CarServiceModel> getAllCars() {
        return this.carRepository
                .findAll()
                .stream()
                .map(c -> this.modelMapper.map(c, CarServiceModel.class))
                .collect(Collectors.toList());
    }
}
