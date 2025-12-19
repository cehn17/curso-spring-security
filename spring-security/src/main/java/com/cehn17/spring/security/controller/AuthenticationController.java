package com.cehn17.spring.security.controller;

import com.cehn17.spring.security.dto.auth.AuthenticationRequest;
import com.cehn17.spring.security.dto.auth.AuthenticationResponse;
import com.cehn17.spring.security.persistence.entity.User;
import com.cehn17.spring.security.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/validate-token")
    public ResponseEntity<Boolean> validate(@RequestParam String jwt){
        boolean isTokenValid = authenticationService.validateToken(jwt);
        return ResponseEntity.ok(isTokenValid);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest authenticationRequest
    ){
        AuthenticationResponse rsp = authenticationService.login(authenticationRequest);
        return ResponseEntity.ok(rsp);
    }

    @GetMapping("/profile")
    public ResponseEntity<User> findMyProfile(){
        User user = authenticationService.findLoggedInUser();
        return ResponseEntity.ok(user);
    }



}
