package com.example.projekt.sevices;

import com.example.projekt.models.User;
import com.example.projekt.repositories.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl  implements UserDetailsService  {

    private  UserRepository userRepository;

    @Lazy
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        //todo throw if not exist
        return userRepository.findByEmail(email);
    }

    public void addUser(User user)
    {

        user.setRole("ROLE_USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User loadUserByEmail(String email) throws UsernameNotFoundException{
        return userRepository.findByEmail(email);
    }

    public List<User> getAll()
    {
        return userRepository.findAll();
    }

    public void saveAll(List <User> users)
    {
        for (int i = 0; i < users.size(); i++)
        {
            users.get(i).setPassword(passwordEncoder.encode(users.get(i).getPassword()));
        }

        userRepository.saveAll(users);
    }

}
