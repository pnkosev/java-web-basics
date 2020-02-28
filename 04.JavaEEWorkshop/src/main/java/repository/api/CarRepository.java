package repository.api;

import domain.entity.Car;

public interface CarRepository extends BaseRepository<Car, Integer> {
    Car update(Car entity);
}
