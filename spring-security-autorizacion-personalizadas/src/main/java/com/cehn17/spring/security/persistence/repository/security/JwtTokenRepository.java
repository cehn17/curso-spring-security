package com.cehn17.spring.security.persistence.repository.security;

import com.cehn17.spring.security.persistence.entity.security.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface JwtTokenRepository extends JpaRepository<JwtToken, Long> {
}
