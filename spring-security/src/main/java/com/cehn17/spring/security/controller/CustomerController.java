package com.cehn17.spring.security.controller;

import com.cehn17.spring.security.dto.RegisteredUser;
import com.cehn17.spring.security.dto.SaveUser;
import com.cehn17.spring.security.persistence.entity.User;
import com.cehn17.spring.security.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<RegisteredUser> registerOne(@RequestBody @Valid SaveUser newUser) {
        RegisteredUser registerUser = authenticationService.registerOneCustomeer(newUser);
        System.out.println(registerUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerUser);
    }

}
