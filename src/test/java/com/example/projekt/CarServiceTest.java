package com.example.projekt;

import com.example.projekt.models.Car;
import com.example.projekt.sevices.CarService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
public class CarServiceTest {

    @Autowired
    private CarService carService;




    @BeforeEach
    public void setup()
    {

    }
    @AfterEach
    public void afterEach()
    {
        carService.delete(carService.getAll().iterator().next().getId());
    }

    @Test
    public void addTest()
    {
        Car car = new Car();
        carService.add(car);
        System.out.println(carService.getAll().size());
        assert carService.getAll().size() == 3;
    }
}