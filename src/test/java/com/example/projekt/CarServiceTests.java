package com.example.projekt;
import com.example.projekt.models.Car;
import com.example.projekt.sevices.CarService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CarServiceTests {

    @Autowired
    private CarService carService;

    @Test
    public void addTest()
    {
        Car car = new Car();
        carService.add(car);
        System.out.println(carService.getAll().size());
        assert carService.getAll().size() == 3;
        carService.delete(car.getId());
    }

    @Test
    public void deleteTest()
    {
        Car car = new Car();
        carService.add(car);
        assert carService.getAll().size() == 3;

        carService.delete(car.getId());
        assert carService.getAll().size() == 2;

    }

    @Test
    public void showTest()
    {
        Car car = carService.show(2L);
        assert car.getBrand() == "FSO Polonez";
    }

    @Test
    public void getAllTest()
    {
        List<Car> carsList = carService.getAll();
        assert carsList.size() == 2;
    }
}
