package com.example.projekt.controllers;

import com.example.projekt.models.Car;
import com.example.projekt.sevices.CarService;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CarController {

    private CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }
    //CAR PANEL

    @GetMapping("admin/dashboard/cars/show/all")
    public String getAllCars(Model model)
    {

        model.addAttribute("cars",carService.getAll());

        return "cars-admin-panel";
    }
    @GetMapping("/admin/dashboard/cars/create")
    public String carShowCreateForm(Model model)
    {
        model.addAttribute("car", new Car());
        return "add-car-admin-panel";
    }

    @PostMapping("/admin/dashboard/cars/save")
    public String saveCar(@ModelAttribute Car car)
    {
        carService.add(car);

        return "redirect:/admin/dashboard/cars/show/all";
    }
    @GetMapping("/admin/dashboard/cars/delete/{id}")
    public String carsDelete(@PathVariable("id") Long id)
    {

        carService.delete(id);
        return "redirect:/admin/dashboard/cars/show/all";
    }
    @GetMapping("/admin/dashboard/cars/edit/{id}")
    public String carsShowEditForm(@PathVariable ("id")Long id,Model model)
    {
        Car car = carService.show(id);
        model.addAttribute("car",car);
        return "edit-car-admin-panel";
    }

    //CAR USER
    @GetMapping("/offers/show/all")
    public String getOffers(Model model)
    {
        model.addAttribute("cars",carService.getAll());

        return "offers";
    }
}
