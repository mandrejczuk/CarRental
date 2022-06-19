package com.example.projekt.controllers;

import com.example.projekt.models.Car;
import com.example.projekt.models.Reservation;
import com.example.projekt.models.User;
import com.example.projekt.repositories.UserRepository;
import com.example.projekt.sevices.CarService;
import com.example.projekt.sevices.ReservationService;
import com.example.projekt.sevices.UserServiceImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class UserController {

    private UserServiceImpl userService;

    private CarService carService;

    private ReservationService reservationService;

    private UserRepository userRepository;

    public UserController(UserServiceImpl userService, CarService carService, ReservationService reservationService, UserRepository userRepository) {

        this.userService = userService;
        this.carService = carService;
        this.reservationService = reservationService;
        this.userRepository = userRepository;
    }

    @GetMapping("/home")
    public String home()
    {
        return "home";
    }
    @GetMapping("/sign-up")
    public String singup(Model model)
    {
        model.addAttribute("user", new User());
        String s = "sing-up";
        return "sign-up";
    }

    @PostMapping("/register")
    public String register(User user)
    {

        userService.registerUser(user);
        String s = "login";
        return s;
    }
    @GetMapping("/login")
    public String login()
    {
        return "login";
    }





    // USER PANEL

    @GetMapping("admin/dashboard/users/show/all")
    public String getAllUser(Model model)
    {

        model.addAttribute("users",userService.getAll());

        return "users-admin-panel";
    }
    @GetMapping("admin/dashboard/users/create")
    public String userShowCreateForm(Model model)
    {
        model.addAttribute("user", new User());
        return "add-user-admin-panel";
    }

    @PostMapping("admin/dashboard/users/save")
    public String saveUser(@ModelAttribute User user)
    {
        userService.addUser(user);

        return "redirect:/admin/dashboard/users/show/all";
    }

    @GetMapping("admin/dashboard/users/edit/{id}")
    public String usersShowEditForm(@PathVariable ("id")Long id,Model model)
    {
        User user = userService.findbyId(id).get();
        model.addAttribute("user",user);
        return "edit-user-admin-panel";
    }
    @GetMapping("admin/dashboard/users/delete/{id}")
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

    @GetMapping("/admin/dashboard")
    public String getDashboard(Principal principal) {

        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated())
        {
            User user = userService.loadUserByEmail(principal.getName());

            if (user.getRole() == "ROLE_ADMIN") {
                return "admin-panel";
            } else {
                return "home";
            }
        }
        else
        {
            return "login";
        }

    }
}
