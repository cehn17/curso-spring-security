package com.cehn17.spring.security.service.impl;

import com.cehn17.spring.security.dto.SaveUser;
import com.cehn17.spring.security.exception.InvalidPasswordException;
import com.cehn17.spring.security.persistence.entity.User;
import com.cehn17.spring.security.persistence.repository.UserRepository;
import com.cehn17.spring.security.persistence.util.Role;
import com.cehn17.spring.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerOneCustomer(SaveUser newUser) {

        validatePassword(newUser);

        User user = new User();
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setName(newUser.getName());
        user.setUsername(newUser.getUsername());
        user.setRole(Role.CUSTOMER);

        return userRepository.save(user);
    }

    @Override
    public Optional<User> findOneByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private void validatePassword(SaveUser dto) {

        if(!StringUtils.hasText(dto.getPassword()) || !StringUtils.hasText(dto.getRepeatedPassword())) {
            throw new InvalidPasswordException("Passwords don't match");
        }
        if(!dto.getPassword().equals(dto.getRepeatedPassword())){
            throw new InvalidPasswordException("Passwords don't match");
        }
    }
}
