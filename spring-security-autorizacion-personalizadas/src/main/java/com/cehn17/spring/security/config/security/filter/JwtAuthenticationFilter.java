package com.cehn17.spring.security.config.security.filter;


import com.cehn17.spring.security.exception.ObjectNotFoundException;
import com.cehn17.spring.security.persistence.entity.security.JwtToken;
import com.cehn17.spring.security.persistence.entity.security.User;
import com.cehn17.spring.security.persistence.repository.security.JwtTokenRepository;
import com.cehn17.spring.security.service.UserService;
import com.cehn17.spring.security.service.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private JwtTokenRepository jwtTokenRepository;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //1. Obtener authorization header
        //2. Obtener token
        String jwt = jwtService.extractJwtFromRequest(request);
        if(jwt == null || !StringUtils.hasText(jwt)){
            filterChain.doFilter(request, response);
            return;
        }

        //2.1 Obtener token no expirado y valido desde base de datos
        Optional<JwtToken> token = jwtTokenRepository.findByToken(jwt);
        boolean isValid = validateToken(token) ;

        if(!isValid) {
            filterChain.doFilter(request, response);
            return;
        }

        //3. Obtener el subject/username desde el token esta accion a su vez valida el formato del token, firma y fecha de expiración
        String username = jwtService.extractUsername(jwt);

        //4. Setear objeto authentication dentro de security context holder

        User user = userService.findOneByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("User not found. Username: " + username));

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username, null, user.getAuthorities()
        );

        authToken.setDetails(new WebAuthenticationDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authToken);

        //5. Ejecutar el registro de filtros
        filterChain.doFilter(request, response);


    }

    private boolean validateToken(Optional<JwtToken> opcionalJwtToken) {
        if(!opcionalJwtToken.isPresent()){
            System.out.println ("Token no existe o no fue generado en nuestro sistema");
            return false;
        }

        JwtToken token = opcionalJwtToken.get();
        Date now = new Date(System.currentTimeMillis());
        boolean isValid = token.isValid() && token.getExpiration().after(now);
        if(!isValid) {
            System.out.println("Token inválido");
            updateTokenStatus(token);
        }
        return isValid;
    }

    private void updateTokenStatus(JwtToken token) {
        token.setValid(false);
        jwtTokenRepository.save(token);
    }
}
