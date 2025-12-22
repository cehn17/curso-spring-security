package com.cehn17.spring.security.service.auth;

import com.cehn17.spring.security.dto.RegisteredUser;
import com.cehn17.spring.security.dto.SaveUser;
import com.cehn17.spring.security.dto.auth.AuthenticationRequest;
import com.cehn17.spring.security.dto.auth.AuthenticationResponse;
import com.cehn17.spring.security.exception.ObjectNotFoundException;
import com.cehn17.spring.security.persistence.entity.security.User;
import com.cehn17.spring.security.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public RegisteredUser registerOneCustomeer(SaveUser newUser) {
        User user = userService.registerOneCustomer(newUser);

        RegisteredUser userDto = new RegisteredUser();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setRole(user.getRole().getName());

        String jwt = jwtService.generateToken(user, generateExtraClaims(user));
        userDto.setJwt(jwt);

        return userDto;
    }

    private Map<String, Object> generateExtraClaims(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getName());
        extraClaims.put("role",  user.getRole().getName());
        extraClaims.put("authorities", user.getAuthorities());

        return extraClaims;
    }

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(),
                authenticationRequest.getPassword()
        );

        authenticationManager.authenticate(authentication);

        UserDetails user = userService.findOneByUsername(authenticationRequest.getUsername()).get();

        String jwt = jwtService.generateToken(user, generateExtraClaims((User) user));

        AuthenticationResponse response = new AuthenticationResponse();

        response.setJwt(jwt);
        return response;
    }

    public boolean validateToken(String jwt) {

        try{
            jwtService.extractUsername(jwt);
            return true;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }

    }

    public User findLoggedInUser() {

        UsernamePasswordAuthenticationToken auth =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        String username = (String) auth.getPrincipal();
        return userService.findOneByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("User not found. Username: " + username));
    }

    public void logout(HttpServletRequest request) {
    }
}
