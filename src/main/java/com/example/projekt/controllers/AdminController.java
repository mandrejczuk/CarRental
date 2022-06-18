package com.example.projekt.controllers;

import com.example.projekt.models.Car;
import com.example.projekt.models.Reservation;
import com.example.projekt.models.User;
import com.example.projekt.sevices.CarService;
import com.example.projekt.sevices.ReservationService;
import com.example.projekt.sevices.UserServiceImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserServiceImpl userService;
    private  CarService carService;
    private ReservationService reservationService;

    public AdminController(UserServiceImpl userService, CarService carService, ReservationService reservationService) {
        this.userService = userService;
        this.carService = carService;
        this.reservationService = reservationService;
    }
    @GetMapping("/dashboard")
    public String getDashboard(Principal principal)
    {
        User user = userService.loadUserByEmail(principal.getName());

        if(user.getRole() == "ROLE_ADMIN")
        {
            return "admin-panel";
        }
        else
        {
            return "home";
        }

    }


    // USER PANEL


    @GetMapping("/dashboard/users/show/all")
    public String getAllUser(Model model)
    {

        model.addAttribute("users",userService.getAll());

        return "users-admin-panel";
    }
    @GetMapping("/dashboard/users/create")
    public String userShowCreateForm(Model model)
    {
        model.addAttribute("user", new User());
        return "add-user-admin-panel";
    }

    @PostMapping("/dashboard/users/save")
    public String saveUser(@ModelAttribute User user)
    {
           userService.addUser(user);

        return "redirect:/admin/dashboard/users/show/all";
    }

    @GetMapping("/dashboard/users/edit/{id}")
    public String usersShowEditForm(@PathVariable ("id")Long id,Model model)
    {
        User user = userService.findbyId(id).get();
        model.addAttribute("user",user);
       return "edit-user-admin-panel";
    }
    @GetMapping("/dashboard/users/delete/{id}")
    public String usersDelete(@PathVariable ("id") Long id)
    {

        if(SecurityContextHolder.getContext().getAuthentication().getName() == userService.findbyId(id).get().getEmail())
        {
            userService.deleteUserbyId(id);
            return "redirect:/logout";
        }
        else
        {
            userService.deleteUserbyId(id);
            return "redirect:/admin/dashboard/users/show/all";
        }
    }



    //CAR PANEL



    @GetMapping("/dashboard/cars/show/all")
    public String getAllCars(Model model)
    {

        model.addAttribute("cars",carService.getAll());

        return "cars-admin-panel";
    }
    @GetMapping("/dashboard/cars/create")
    public String carShowCreateForm(Model model)
    {
        model.addAttribute("car", new Car());
        return "add-car-admin-panel";
    }

    @PostMapping("/dashboard/cars/save")
    public String saveCar(@ModelAttribute Car car)
    {
        carService.add(car);

        return "redirect:/admin/dashboard/cars/show/all";
    }
    @GetMapping("/dashboard/cars/delete/{id}")
    public String carsDelete(@PathVariable ("id") Long id)
    {

            carService.delete(id);
            return "redirect:/admin/dashboard/cars/show/all";
    }
    @GetMapping("/dashboard/cars/edit/{id}")
    public String carsShowEditForm(@PathVariable ("id")Long id,Model model)
    {
        Car car = carService.show(id);
        model.addAttribute("car",car);
        return "edit-car-admin-panel";
    }



    //RESERVATION PANEL



    @GetMapping("/dashboard/reservations/show/all")
    public String getAllReservations(Model model)
    {

        model.addAttribute("reservations",reservationService.getAll());

        return "reservations-admin-panel";
    }
    @GetMapping("/dashboard/reservations/create")
    public String reservationShowCreateForm(Model model)
    {
        model.addAttribute("reservation", new Reservation());
        model.addAttribute("cars", carService.getAll());
        model.addAttribute("users",userService.getAll());
        return "add-reservation-admin-panel";
    }

    @PostMapping("/dashboard/reservations/save")
    public String saveReservation(Reservation reservation)
    {
        reservationService.add(reservation);

        return "redirect:/admin/dashboard/reservations/show/all";
    }
    @GetMapping("/dashboard/reservations/delete/{id}")
    public String reservationDelete(@PathVariable ("id") Long id)
    {

        reservationService.delete(id);
        return "redirect:/admin/dashboard/reservations/show/all";
    }
    @GetMapping("/dashboard/reservations/edit/{id}")
    public String reservationsShowEditForm(@PathVariable ("id")Long id,Model model)
    {
        Reservation reservation = reservationService.show(id);
        model.addAttribute("reservation",reservation);
        model.addAttribute("cars", carService.getAll());
        model.addAttribute("users",userService.getAll());
        return "edit-reservation-admin-panel";
    }
}
