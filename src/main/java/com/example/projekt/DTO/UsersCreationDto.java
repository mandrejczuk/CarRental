package com.example.projekt.DTO;

import com.example.projekt.models.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;


public class UsersCreationDto {
    private List <User> users = new LinkedList<>();

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public UsersCreationDto(List<User> users) {
        this.users = users;
    }

    public UsersCreationDto() {
    }

    public void addUser(User user)
    {
        this.users.add(user);
    }
}
