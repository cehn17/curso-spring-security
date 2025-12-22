package com.cehn17.spring.security.controller;

import com.cehn17.spring.security.dto.RegisteredUser;
import com.cehn17.spring.security.dto.SaveUser;
import com.cehn17.spring.security.persistence.entity.security.User;
import com.cehn17.spring.security.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private AuthenticationService authenticationService;

    @PreAuthorize("permitAll")
    @PostMapping
    public ResponseEntity<RegisteredUser> registerOne(@RequestBody @Valid SaveUser newUser) {
        RegisteredUser registerUser = authenticationService.registerOneCustomeer(newUser);
        System.out.println(registerUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerUser);
    }

    @PreAuthorize("denyAll")
    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok(Arrays.asList());
    }

}
