package com.example.projekt.sevices;

import com.example.projekt.models.User;
import com.example.projekt.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl  implements UserDetailsService  {

    private  UserRepository userRepository;
    private Logger logger = LoggerFactory.getLogger(UserDetails.class);


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

    public void registerUser(User user)
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
    public Optional<User> findbyId(Long id)
    {
        return userRepository.findById(id);

    }
    public void addUser(User user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    public void deleteUserbyId(Long id)
    {
        userRepository.delete(userRepository.getOne(id));
    }

    public boolean isEmailUnique(String email)
    {
        if(userRepository.existsUserByEmail(email))
        {
            return false;
        }
        else
        {
            return true;
        }
    }


}
