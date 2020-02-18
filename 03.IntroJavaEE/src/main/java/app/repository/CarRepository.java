package app.repository;

import app.domain.entities.Car;

import java.util.ArrayList;
import java.util.List;

public final class CarRepository {

    private final static List<Car> cars = new ArrayList<>();

    public static void addCar(Car car) {
        cars.add(car);
    }

    public static List<Car> getAllCars() {
        return cars;
    }
}
