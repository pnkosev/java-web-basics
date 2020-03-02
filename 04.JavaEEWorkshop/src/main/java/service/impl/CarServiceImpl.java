package service.impl;

import domain.entity.Car;
import domain.entity.User;
import domain.model.service.CarServiceModel;
import org.modelmapper.ModelMapper;
import repository.api.CarRepository;
import repository.api.UserRepository;
import service.api.CarService;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class CarServiceImpl implements CarService {

    private final UserRepository userRepository;

    private final CarRepository carRepository;

    private final ModelMapper modelMapper;

    @Inject
    public CarServiceImpl(UserRepository userRepository, CarRepository carRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createCar(CarServiceModel carServiceModel) {
        String name = carServiceModel.getUser().getUsername();
        User user = this.userRepository.findByUsername(name);

        Car car = this.modelMapper.map(carServiceModel, Car.class);

        car.setUser(user);
        user.getCars().add(car);

        this.userRepository.merge(user);
//        this.carRepository.save(car); // IF I LEAVE THIS AND THE MERGE OF THE USER- THE CAR GETS REGISTERED TWICE
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
