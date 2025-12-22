package com.cehn17.spring.security.service;

import com.cehn17.spring.security.persistence.entity.security.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findDefaultRole();
}
