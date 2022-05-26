package com.example.projekt.sevices;

import com.example.projekt.models.Car;
import com.example.projekt.repositories.CarRepository;
import org.springframework.stereotype.Service;

@Service
public class CarService extends GenericManagementService<Car, CarRepository> {
    public CarService(CarRepository repository) {
        super(repository);
    }
}
