package com.example.projekt;
import com.example.projekt.models.Car;
import com.example.projekt.models.Reservation;
import com.example.projekt.models.User;
import com.example.projekt.sevices.ReservationService;
import com.example.projekt.sevices.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    ReservationService reservationService;

    @Test
    public void addUserTest() {
        User user = new User();
        user.setPassword("123");
        userService.addUser(user);
        assert userService.getAll().size() == 3;
        userService.deleteUserbyId(user.getId());
    }

    @Test
    public void deleteUserTest() {
        User user = new User();
        user.setEmail("abc@abc");
        user.setPassword("123");
        userService.addUser(user);
        userService.deleteUserbyId(user.getId());
        List<User> users = userService.getAll();
        int a = users.size();
        assert userService.getAll().size() == 2;
    }

    @Test
    public void registerUserTest()
    {
        User user = new User();
        user.setEmail("user2@user");
        user.setPassword("123");
        userService.registerUser(user);
        assert user.getRole() == "ROLE_USER";
        userService.deleteUserbyId(user.getId());
    }

    @Test
    public void loadUserByEmailTest()
    {
        User user = new User();
        user.setEmail("user1@user");
        user.setPassword("123");
        userService.addUser(user);
        assert userService.loadUserByEmail("user1@user").getEmail() == user.getEmail();
        userService.deleteUserbyId(user.getId());
    }

    @Test
    public void getAllTest()
    {
        List<User> users = userService.getAll();
        assert users.size() == userService.getAll().size();
    }

    @Test
    public void deleteUserAndHisReservationsTest()
    {
        User user = new User();
        user.setEmail("user1@user");
        user.setPassword("123");
        userService.registerUser(user);
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2023,10,12));
        reservation.setEndDate(LocalDate.of(2023,12,12));
        reservation.setUser(user);
        reservationService.add(reservation);

        assert userService.getAll().size() == 3;
        assert reservationService.getAll().size() == 3;

        userService.deleteUserbyId(user.getId());

        assert userService.getAll().size() == 2;
        assert reservationService.getAll().size() == 2;
    }
}
