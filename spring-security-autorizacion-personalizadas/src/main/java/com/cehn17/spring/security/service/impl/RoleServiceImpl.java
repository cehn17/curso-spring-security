package com.cehn17.spring.security.service.impl;

import com.cehn17.spring.security.persistence.entity.security.Role;
import com.cehn17.spring.security.persistence.repository.security.RoleRepository;
import com.cehn17.spring.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Value("${security.default.role}")
    private String defaultRole;

    @Override
    public Optional<Role> findDefaultRole() {
        return roleRepository.findByName(defaultRole);
    }
}
