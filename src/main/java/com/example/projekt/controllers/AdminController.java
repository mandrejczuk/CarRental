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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        UsersCreationDto usersForm = new UsersCreationDto();
            usersForm.addUser(new User());
        model.addAttribute("form", usersForm);
        return "add-user-admin-panel";
    }

    @PostMapping("/dashboard/users/save")
    public String saveUsers(@ModelAttribute UsersCreationDto form, Model model)
    {
            if (form.getUsers().get(0).getEmail().isBlank() || form.getUsers().get(0).getPassword().isBlank())
            {

            }
            else {
                userService.saveAll(form.getUsers());
            }
        model.addAttribute("users",userService.getAll());

        return "redirect:/admin/dashboard/users/show/all";
    }

    @GetMapping("/dashboard/users/edit")
    public String usersShowEditForm(Model model)
    {
        List <User> users = new ArrayList<>();
        userService.getAll().iterator().forEachRemaining(users::add);
        model.addAttribute("form", new UsersCreationDto(users));
        return "edit-user-admin-panel";
    }

}
