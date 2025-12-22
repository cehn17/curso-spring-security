package com.cehn17.spring.security.service;

import com.cehn17.spring.security.dto.SaveUser;
import com.cehn17.spring.security.persistence.entity.security.User;

import java.util.Optional;

public interface UserService {

    public User registerOneCustomer(SaveUser newUser);

    Optional<User> findOneByUsername(String username);
}
