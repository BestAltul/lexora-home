package com.lexorahome.Lexora.main.auth_service.service;

import com.lexorahome.Lexora.main.auth_service.entity.Role;
import com.lexorahome.Lexora.main.auth_service.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleInitializer {
    private final RoleRepository roleRepository;

    @PostConstruct
    public void initRoles() {
        createRoleIfNotExists("ADMIN");
        createRoleIfNotExists("USER");
    }

    private void createRoleIfNotExists(String roleName) {
        roleRepository.findByName(roleName)
                .orElseGet(() -> roleRepository.save(new Role(roleName)));
    }
}
