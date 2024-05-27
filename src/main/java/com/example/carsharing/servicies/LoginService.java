package com.example.carsharing.servicies;

import com.example.carsharing.entities.Users;
import com.example.carsharing.repos.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    UsersRepository usersRepository;
    public Users getUser(int id) {
        return usersRepository.findById(id).orElse(null);
    }
    public void saveUser(Users user) {
        usersRepository.save(user);
    }
}
