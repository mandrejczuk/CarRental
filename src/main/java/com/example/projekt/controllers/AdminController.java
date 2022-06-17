package com.example.projekt.controllers;

import com.example.projekt.DTO.UsersCreationDto;
import com.example.projekt.models.User;
import com.example.projekt.sevices.CarService;
import com.example.projekt.sevices.ReservationService;
import com.example.projekt.sevices.UserServiceImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

}
